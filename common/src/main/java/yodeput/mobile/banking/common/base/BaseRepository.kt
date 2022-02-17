package yodeput.mobile.banking.common.base

import android.content.Context
import yodeput.mobile.banking.common.util.ErrorCode
import yodeput.mobile.banking.common.util.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository<T>(
    context: Context,
    coroutinesDispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun query(): T

    protected abstract suspend fun fetch(): T

    protected abstract suspend fun saveFetchResult(items: T)

    protected open fun isNotEmpty(t: T) = t != null

    val result: Flow<ViewState<T>> = flow {
        emit(ViewState.Loading)
        query().let {
            if (isNotEmpty(it)) {
                // ****** STEP 1: VIEW CACHE ******
                emit(ViewState.Success(it))
            }
            try {
                // ****** STEP 2: MAKE NETWORK CALL, SAVE RESULT TO CACHE ******
                saveFetchResult(fetch())
                // ****** STEP 3: VIEW CACHE ******
                emit(ViewState.Success(query()))
            } catch (t: Throwable) {
                if (isNotEmpty(it)) {
                    return@flow
                }
                emit(ViewState.Error(ErrorCode(1000, "BaseRepository Exception")))
            }

        }
    }.flowOn(coroutinesDispatcher)
}