package yodeput.mobile.banking.core.response


import android.os.Parcelable
import yodeput.mobile.banking.core.model.Transaction
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionsResponse(
    @Json(name = "data") val data: List<Transaction>? = null,
    @Json(name = "status") val status: String? = null
) : Parcelable