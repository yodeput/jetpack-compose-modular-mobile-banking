package yodeput.mobile.banking.core.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class TransferResponse(
    @Json(name = "amount") val amount: Int? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "recipientAccount") val recipientAccount: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "transactionId") val transactionId: String? = null
) : Parcelable