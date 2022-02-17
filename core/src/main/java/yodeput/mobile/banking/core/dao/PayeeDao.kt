package yodeput.mobile.banking.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yodeput.mobile.banking.core.database.PayeeDb
import yodeput.mobile.banking.core.database.TransactionDb
@Dao
interface PayeeDao {
    @Query("SELECT * FROM 'payee' WHERE id = 1")
    suspend fun get(): TransactionDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PayeeDb)

    @Query("DELETE FROM 'payee'")
    suspend fun clear()
}