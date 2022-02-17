package yodeput.mobile.banking.core.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Balance(
    @Json(name = "balance") val balance: String? = null,
    @Json(name = "balanceDouble") val balanceDouble: Double? = null
) : Parcelable