package yodeput.mobile.banking.core.model


import android.os.Parcelable
import yodeput.mobile.banking.core.database.UserDb
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @Json(name = "accountNo") val accountNo: String? = null,
    @Json(name = "token") val token: String? = null,
    @Json(name = "username") val username: String? = null
) : Parcelable

fun User.asDatabaseModel(): UserDb {
    return UserDb(
        id = 1,
        accountNo = accountNo!!,
        token = token!!,
        username = username!!,
    )
}