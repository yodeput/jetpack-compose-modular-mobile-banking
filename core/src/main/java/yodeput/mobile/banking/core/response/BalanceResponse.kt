package yodeput.mobile.banking.core.response


import android.os.Parcelable
import yodeput.mobile.banking.common.util.NumberUtils.currencyFormat
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class BalanceResponse(
    @Json(name = "accountNo") val accountNo: String? = null,
    @Json(name = "balance") val balance: Double? = null,
    @Json(name = "status") val status: String? = null
) : Parcelable {
     val balanceFormated: String get() = currencyFormat(balance!!)

}

