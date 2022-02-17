package yodeput.mobile.banking.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import yodeput.mobile.banking.core.model.Payees

@Entity(tableName = "payee")
class PayeeDb(
    @PrimaryKey val id: Long,
    val description: String,
    val data: List<Payees>,
)

