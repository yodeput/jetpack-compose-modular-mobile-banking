package yodeput.mobile.banking.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import yodeput.mobile.banking.core.model.Transaction

@Entity(tableName = "transaction")
class TransactionDb(
    @PrimaryKey val id: Long,
    val description: String,
    val data: List<Transaction>,
)

