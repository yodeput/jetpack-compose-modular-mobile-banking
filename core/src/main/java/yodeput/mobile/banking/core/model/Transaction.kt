package yodeput.mobile.banking.core.model


import android.os.Parcelable
import yodeput.mobile.banking.common.util.DateUtils
import yodeput.mobile.banking.common.util.NumberUtils
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Transaction(
    @Json(name = "amount") val amount: Double? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "receipient") val receipient: Receipient? = null,
    @Json(name = "transactionDate") val transactionDate: String? = null,
    @Json(name = "transactionId") val transactionId: String? = null,
    @Json(name = "transactionType") val transactionType: String? = null
) : Parcelable {
    val dateFormated: String get() = DateUtils.dateToString(DateUtils.stringToDateTime(transactionDate!!)!!)!!
    val dateHumanFormated: String get() = DateUtils.dateToString(DateUtils.stringToDateTime(transactionDate!!)!!, DateUtils.DMMMYYYY_FORMAT)!!

    val amountFormated: String get() = NumberUtils.currencyFormat(amount!!)
}