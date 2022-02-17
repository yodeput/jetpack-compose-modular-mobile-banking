package yodeput.mobile.banking.core.response


import android.os.Parcelable
import yodeput.mobile.banking.core.model.Payees
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayeesResponse(
    @Json(name = "data") val data: List<Payees>? = null,
    @Json(name = "status") val status: String? = null
) : Parcelable