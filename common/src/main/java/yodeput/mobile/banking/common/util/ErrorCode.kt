package yodeput.mobile.banking.common.util

/**
 * A customized pokemon error response.
 *
 * @param code A network response code.
 * @param message A network error message.
 */
data class ErrorCode(
  val code: Int,
  val message: String?
) {
  override fun toString(): String {
    return "ErrorCode ===> (code=$code, message=$message)"
  }
}
