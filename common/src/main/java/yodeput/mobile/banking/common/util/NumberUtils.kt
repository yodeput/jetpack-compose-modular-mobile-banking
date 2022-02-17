package yodeput.mobile.banking.common.util

import java.text.NumberFormat
import java.util.*

object NumberUtils {

    fun currencyFormat(number: Double): String {
        return NumberFormat.getCurrencyInstance(Locale.US).format(number)
    }
    fun numberFormat(number: Double): String {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }
    fun isNumber(string: String): Boolean {
        return string.matches("^[0-9]*$".toRegex())
    }
}