package yodeput.mobile.banking.core.response


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ErrorResponse(
    @Json(name = "error") val error: String? = null,
    @Json(name = "status") val status: String? = null
) : Parcelable