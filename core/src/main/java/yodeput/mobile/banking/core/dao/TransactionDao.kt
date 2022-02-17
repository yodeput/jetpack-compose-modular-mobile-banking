package yodeput.mobile.banking.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yodeput.mobile.banking.core.database.TransactionDb
@Dao
interface TransactionDao {
    @Query("SELECT * FROM 'transaction' WHERE id = 1")
    suspend fun get(): TransactionDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: TransactionDb)

    @Query("DELETE FROM 'transaction'")
    suspend fun clear()
}