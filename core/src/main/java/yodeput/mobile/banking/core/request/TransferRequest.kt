package yodeput.mobile.banking.core.request


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransferRequest(
    @Json(name = "amount") val amount: Int? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "receipientAccountNo") val receipientAccountNo: String? = null
) : Parcelable