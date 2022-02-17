package yodeput.mobile.banking.core.response


import android.os.Parcelable
import androidx.room.Entity
import yodeput.mobile.banking.core.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "accountNo") val accountNo: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "error") val error: String? = null,
    @Json(name = "token") val token: String? = null,
    @Json(name = "username") val username: String? = null
) : Parcelable

fun LoginResponse.asUser(): User {
    return User(
        accountNo = accountNo,
        token = token,
        username = username,
    )
}
