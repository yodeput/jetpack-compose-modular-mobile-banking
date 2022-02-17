package yodeput.mobile.banking.core.repository


import yodeput.mobile.banking.common.base.Repository
import yodeput.mobile.banking.common.util.ErrorCode
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.dao.TransactionDao
import yodeput.mobile.banking.core.database.TransactionDb
import yodeput.mobile.banking.core.di.IoDispatcher
import yodeput.mobile.banking.core.model.Transaction
import yodeput.mobile.banking.core.network.TrxService
import yodeput.mobile.banking.core.request.TransferRequest
import yodeput.mobile.banking.core.response.TransferResponse
import com.skydoves.sandwich.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import yodeput.mobile.banking.core.mapper.ErrorResponseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val dao: TransactionDao,
    private val api: TrxService,
    @IoDispatcher private val coroutinesDispatcher: CoroutineDispatcher
) : Repository {

    fun getData(
        isRefresh: Boolean
    ): Flow<ViewState<List<Transaction>>> = flow {
        dao.get().let {
            if (it != null) {
                if (isRefresh) {
                    val res = api.transactions()
                    res.suspendOnSuccess {
                        data.data!!.let {
                            emit(ViewState.Success(it))
                            dao.insert(
                                TransactionDb(
                                    id = 1,
                                    description = "trx",
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
                    emit(ViewState.Success(it.data as List<Transaction>))
                }
            } else {
                val res = api.transactions()
                res.suspendOnSuccess {
                    data.data!!.let {
                        emit(ViewState.Success(it))
                        dao.insert(
                            TransactionDb(
                                id = 1,
                                description = "trx",
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

    @OptIn(FlowPreview::class)
    fun transferApi(
        body: TransferRequest,
    ): Flow<ViewState<TransferResponse>> = flow {

        val res = api.transfer(body)
        res.suspendOnSuccess {
            emit(ViewState.Success(data))
        }.suspendOnError {
            val error = map(ErrorResponseMapper)
            emit(ViewState.Error(error))
        }.suspendOnException {
            emit(ViewState.Error(ErrorCode(1000, "loginApi Exception")))
        }
    }.onStart { emit(ViewState.Loading) }
        .flowOn(coroutinesDispatcher)

}