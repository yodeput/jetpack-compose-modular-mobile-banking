package yodeput.mobile.banking.core.repository


import yodeput.mobile.banking.common.base.Repository
import yodeput.mobile.banking.common.util.ErrorCode
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.dao.PayeeDao
import yodeput.mobile.banking.core.database.PayeeDb
import yodeput.mobile.banking.core.di.IoDispatcher
import yodeput.mobile.banking.core.model.Payees
import yodeput.mobile.banking.core.network.PayeeService
import com.skydoves.sandwich.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import yodeput.mobile.banking.core.mapper.ErrorResponseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayeeRepository @Inject constructor(
    private val dao: PayeeDao,
    private val api: PayeeService,
    @IoDispatcher private val coroutinesDispatcher: CoroutineDispatcher
) : Repository {

    fun getData(
        isRefresh: Boolean
    ): Flow<ViewState<List<Payees>>> = flow {
        dao.get().let {
            if (it != null) {
                if (isRefresh) {
                    val res = api.payees()
                    res.suspendOnSuccess {
                        data.data!!.let {
                            emit(ViewState.Success(it))
                            dao.insert(
                                PayeeDb(
                                    id = 1,
                                    description = "payees",
                                    data = it
                                )
                            )
                        }
                    }.suspendOnError {
                        val error = map(ErrorResponseMapper)
                        emit(ViewState.Error(error))
                    }.suspendOnException {
                        emit(ViewState.Error(ErrorCode(1000, message())))
                    }
                } else {
                    emit(ViewState.Success(it.data as List<Payees>))
                }
            } else {
                val res = api.payees()
                res.suspendOnSuccess {
                    data.data!!.let {
                        emit(ViewState.Success(it))
                        dao.insert(
                            PayeeDb(
                                id = 2,
                                description = "payees",
                                data = it
                            )
                        )
                    }
                }.suspendOnError {
                    val error = map(ErrorResponseMapper)
                    emit(ViewState.Error(error))
                }.suspendOnException {
                    emit(ViewState.Error(ErrorCode(1000, message())))
                }
            }
        }
    }.onStart { emit(ViewState.Loading) }.flowOn(coroutinesDispatcher)

    fun contactApi(): Flow<ViewState<List<Payees>>> = flow {

        val res = api.payees()
        res.suspendOnSuccess {
            emit(ViewState.Success(data.data!!))
        }.suspendOnError {
            val error = map(ErrorResponseMapper)
            emit(ViewState.Error(error))
        }.suspendOnException {
            emit(ViewState.Error(ErrorCode(1000, "contactApi Exception")))
        }
    }.onStart { emit(ViewState.Loading) }
        .flowOn(coroutinesDispatcher)
}