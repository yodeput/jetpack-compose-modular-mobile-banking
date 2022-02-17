package yodeput.mobile.banking.core.database

import androidx.room.TypeConverter
import yodeput.mobile.banking.core.model.Payees
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object ConverterPayee {
    var gson = Gson()
    @TypeConverter
    fun stringToTransactionList(data: String?): List<Payees> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Payees>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun TransactionListToString(someObjects: List<Payees>): String? {
        return gson.toJson(someObjects)
    }
}