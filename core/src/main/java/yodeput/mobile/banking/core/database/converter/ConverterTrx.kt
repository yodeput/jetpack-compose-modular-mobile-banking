package yodeput.mobile.banking.core.database

import androidx.room.TypeConverter
import yodeput.mobile.banking.core.model.Transaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object ConverterTrx {
    var gson = Gson()
    @TypeConverter
    fun stringToTransactionList(data: String?): List<Transaction> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Transaction>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun TransactionListToString(someObjects: List<Transaction>): String? {
        return gson.toJson(someObjects)
    }
}