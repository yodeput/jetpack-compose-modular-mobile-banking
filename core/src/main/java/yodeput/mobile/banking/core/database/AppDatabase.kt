package yodeput.mobile.banking.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yodeput.mobile.banking.core.BuildConfig
import yodeput.mobile.banking.core.dao.PayeeDao
import yodeput.mobile.banking.core.dao.TransactionDao
import yodeput.mobile.banking.core.dao.UserDao

@Database(
    entities = [UserDb::class, TransactionDb::class, PayeeDb::class],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = BuildConfig.DATABASE_EXPORT_SCHEMA
)
@TypeConverters(ConverterPayee::class, ConverterTrx::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun payeeDao(): PayeeDao
}