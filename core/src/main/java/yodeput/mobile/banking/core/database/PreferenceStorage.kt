package yodeput.mobile.banking.core.database

import android.content.Context
import android.content.SharedPreferences

class PreferenceStorage(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var token: String?
    get() = prefs.getString(TOKEN,null)
    set(value) = prefs.edit().putString(TOKEN,value).apply()

    var balance: String?
        get() = prefs.getString(BALANCE, null)
        set(value) = prefs.edit().putString(BALANCE,value).apply()

    var balanceDouble: Double?
        get() = prefs.getFloat(BALANCE_DOUBLE, 0F).toDouble()
        set(value: Double?) = prefs.edit().putFloat(BALANCE_DOUBLE, value!!.toFloat()).apply()

    fun clear() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val PREFS_FILENAME = "yodeput.mobile.banking.prefs"
        private const val TOKEN = "yodeput.mobile.banking.prefs.token"
        private const val BALANCE = "yodeput.mobile.banking.prefs.balance"
        private const val BALANCE_DOUBLE = "yodeput.mobile.banking.prefs.balanceDouble"

        private var INSTANCE: PreferenceStorage? = null
        private val lock = PreferenceStorage::class.java

        @JvmStatic
        fun getInstance(context: Context): PreferenceStorage {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = PreferenceStorage(context)
                }
                return INSTANCE!!
            }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}