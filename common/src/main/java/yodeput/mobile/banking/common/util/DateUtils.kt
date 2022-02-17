package yodeput.mobile.banking.common.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val SHIPMENT_FORMAT = "EEEE, MMM d"
    private const val TIMESTAMPFORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    private const val SIMPLE_FORMAT = "yyyy-MM-dd"
    const val DMY_FORMAT = "dd/MM/yyyy"
    const val DMMM_FORMAT = "d MMM"
    const val DMMMY_FORMAT = "dd MMMM yyyy"
    const val DMMMYY_FORMAT = "dd MMM yy"
    const val DMMMYYYY_FORMAT = "dd MMM yyyy"
    private const val MD_FORMAT = "MMM d"
    private const val DM_FORMAT = "dd MMM"
    private const val HM_FORMAT = "HH:mm"
    private const val HMA_FORMAT = "HH:mm a"
    private const val EHM_FORMAT = "EEEE, d MMM yyyy HH:mm"
    const val EM_FORMAT = "EEEE, d MMM yyyy"
    private const val E_FORMAT = "EEEE"
    private const val LOG_FORMAT = "MMM d | KK:mm a"
    const val DMYHM_FORMAT = "dd/MM/yyyy HH:mm"
    private val localeId = Locale("in", "rID")
    private val localeEn = Locale("en", "US")

    private fun getLocale(): Locale {
        return localeId
    }


    fun toSimpleFormat(calendar: Calendar): String? {
        val format = SimpleDateFormat(
            SIMPLE_FORMAT,
            getLocale()
        )
        return format.format(calendar.time)
    }

    fun toTimestampFormat(date: Date): String? {
        val format = SimpleDateFormat(
            TIMESTAMPFORMAT,
            getLocale()
        )
        return format.format(date)
    }
    fun stringToDate(stringDate: String, into: String = SIMPLE_FORMAT): Date? {
        val parseFormat = SimpleDateFormat(into,
            getLocale()
        )
        return parseFormat.parse(stringDate)
    }
    fun stringToDateTime(stringDate: String, isUTC: Boolean = false): Date? {
        val parseFormat =
            SimpleDateFormat(
                TIMESTAMPFORMAT,
                getLocale()
            )
        if (isUTC) parseFormat.timeZone = TimeZone.getTimeZone("UTC")
        return parseFormat.parse(stringDate)
    }
    fun dateToString(date: Date = Date(), into: String = SIMPLE_FORMAT): String? {
        val format = SimpleDateFormat(into,
            getLocale()
        )
        return format.format(date)
    }

    fun toEHMS(date: Date): String? {
        val format = SimpleDateFormat(
            TIMESTAMPFORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toDMY(date: Date): String? {
        val format = SimpleDateFormat(
            DMY_FORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toMD(date: Date): String? {
        val format = SimpleDateFormat(
            MD_FORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toDM(date: Date): String? {
        val format = SimpleDateFormat(
            DM_FORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toHM(date: Date): String? {
        val format = SimpleDateFormat(
            HM_FORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toHMA(date: Date): String? {
        val format = SimpleDateFormat(
            HMA_FORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toEHM(date: Date): String? {
        val format = SimpleDateFormat(
            EHM_FORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toLogFormat(date: Date?):String? {
        val format = SimpleDateFormat(
            LOG_FORMAT,
            getLocale()
        )
        return format.format(date)
    }

    fun toMDWithSuffix(date: Date?): String? {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val suffix =
            getDayOfMonthSuffix(day)
        val format = SimpleDateFormat(
            MD_FORMAT,
            getLocale()
        )
        val string = format.format(calendar.time)
        return "$string$suffix"
    }

    fun toFormattedDay(calendar: Calendar): String {
        val now = Calendar.getInstance()
        return when {
            calendar.get(Calendar.DATE) == now.get(Calendar.DATE) -> "Today"
            calendar.get(Calendar.DATE) - now.get(Calendar.DATE) == 1 -> "Tomorrow"
            else -> dateToString(
                calendar.time,
                E_FORMAT
            ) ?: ""
        }
    }

    private fun getDayOfMonthSuffix(n: Int): String {
        if (n in 11..13) {
            return "th"
        }
        return when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}