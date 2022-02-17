package yodeput.mobile.banking.common.base

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseListRepository<T>(
    context: Context,
    coroutinesDispatcher: CoroutineDispatcher
) : BaseRepository<List<T>>(context, coroutinesDispatcher) {

    override fun isNotEmpty(t: List<T>): Boolean = t.isNotEmpty()
}