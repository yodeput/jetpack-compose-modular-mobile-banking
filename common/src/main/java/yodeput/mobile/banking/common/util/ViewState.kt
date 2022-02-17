package yodeput.mobile.banking.common.util

sealed class ViewState<out R> {
    object Idle : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    data class Success<out T>(val data: T) : ViewState<T>()
    data class Error(val errorCode: ErrorCode?) : ViewState<Nothing>()
}