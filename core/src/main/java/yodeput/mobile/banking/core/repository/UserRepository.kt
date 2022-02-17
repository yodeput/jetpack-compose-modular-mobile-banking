package yodeput.mobile.banking.core.repository


import com.skydoves.sandwich.map
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import yodeput.mobile.banking.common.base.Repository
import yodeput.mobile.banking.common.util.ErrorCode
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.dao.PayeeDao
import yodeput.mobile.banking.core.dao.TransactionDao
import yodeput.mobile.banking.core.dao.UserDao
import yodeput.mobile.banking.core.database.PreferenceStorage
import yodeput.mobile.banking.core.database.asDomainModel
import yodeput.mobile.banking.core.di.IoDispatcher
import yodeput.mobile.banking.core.model.Balance
import yodeput.mobile.banking.core.model.User
import yodeput.mobile.banking.core.model.asDatabaseModel
import yodeput.mobile.banking.core.network.AuthService
import yodeput.mobile.banking.core.request.LoginRequest
import yodeput.mobile.banking.core.response.BalanceResponse
import yodeput.mobile.banking.core.response.LoginResponse
import yodeput.mobile.banking.core.response.asUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import yodeput.mobile.banking.core.mapper.ErrorResponseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val pref: PreferenceStorage,
    private val dao: UserDao,
    private val transactionDao: TransactionDao,
    private val payeeDao: PayeeDao,
    private val api: AuthService,
    @IoDispatcher private val coroutinesDispatcher: CoroutineDispatcher
) : Repository {

    @OptIn(FlowPreview::class)
    fun loginApi(
        body: LoginRequest,
    ): Flow<ViewState<LoginResponse>> = flow {

        val res = api.login(body)
        res.suspendOnSuccess {
            save(data.asUser())
            pref.token = data.token
            emit(ViewState.Success(data))
        }.suspendOnError {
            val error = map(ErrorResponseMapper)
            emit(ViewState.Error(error))
        }.suspendOnException {
            emit(ViewState.Error(ErrorCode(1000, "loginApi Exception")))
        }
    }.onStart { emit(ViewState.Loading) }
        .flowOn(coroutinesDispatcher)

    fun registerApi(
        body: LoginRequest,
    ): Flow<ViewState<LoginResponse>> = flow {
        emit(ViewState.Loading)
        val res = api.register(body)
        res.suspendOnSuccess {
            emit(ViewState.Success(data))
        }.suspendOnError {
            val error = map(ErrorResponseMapper)
            emit(ViewState.Error(error))
        }.suspendOnException {
            emit(ViewState.Error(ErrorCode(1000, "registerApi Exception")))
        }
    }.flowOn(coroutinesDispatcher)

    fun getBalanceApi(): Flow<ViewState<BalanceResponse>> = flow {
        emit(ViewState.Loading)
        val res = api.balance()
        res.suspendOnSuccess {
            pref.balance = data.balanceFormated
            pref.balanceDouble = data.balance
            emit(ViewState.Success(data))
        }.suspendOnError {
            val error = map(ErrorResponseMapper)
            emit(ViewState.Error(error))
        }.suspendOnException {
            emit(ViewState.Error(ErrorCode(1000, "getBalance Exception")))
        }
    }.flowOn(coroutinesDispatcher)

    suspend fun save(user: User) {
        return dao.insert(user.asDatabaseModel())
    }

    fun logout(): Flow<ViewState<Boolean>> = flow {
        emit(ViewState.Loading)
        dao.clear()
        transactionDao.clear()
        payeeDao.clear()
        pref.clear()
        delay(850)
        emit(ViewState.Success(true))
    }

    fun getToken(): String {
        pref.token?.let { return it }
        return ""
    }

    suspend fun getUser(): Flow<ViewState<User>> = flow {
        emit(ViewState.Loading)
        val data = dao.getUser()
        data.let {
            if (it != null) {
                emit(ViewState.Success(it.asDomainModel()))
            } else {
                emit(ViewState.Error(ErrorCode(1000, "getUser Exception")))
            }
        }
    }.flowOn(coroutinesDispatcher)

    fun isLoggedIn(): Boolean {
        pref.token?.let { return true }
        return false
    }

    fun getBalance(isRefresh: Boolean = false): Flow<Balance?> = flow {
        if(pref.balance == null) {
            val res = api.balance()
            res.suspendOnSuccess {
                pref.balance = data.balanceFormated
                pref.balanceDouble = data.balance
                emit(Balance(data.balanceFormated, data.balance))
            }
        } else {
            if(isRefresh) {
                val res = api.balance()
                res.suspendOnSuccess {
                    pref.balance = data.balanceFormated
                    pref.balanceDouble = data.balance
                    emit(Balance(data.balanceFormated, data.balance))
                }
            } else {
                emit(Balance(pref.balance, pref.balanceDouble))
            }

        }
    }.flowOn(coroutinesDispatcher)
}
