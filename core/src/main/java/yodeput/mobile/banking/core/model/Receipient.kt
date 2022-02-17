package yodeput.mobile.banking.core.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Receipient(
    @Json(name = "accountHolder") val accountHolder: String? = null,
    @Json(name = "accountNo") val accountNo: String? = null
) : Parcelable