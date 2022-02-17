package yodeput.mobile.banking.core.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Payees(
    @Json(name = "accountNo") val accountNo: String? = null,
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null
) : Parcelable