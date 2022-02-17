package yodeput.mobile.banking.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import yodeput.mobile.banking.core.model.User

@Entity(tableName = "user")
class UserDb(
    @PrimaryKey val id: Long,
    val accountNo: String,
    val token: String,
    val username: String,
)

fun UserDb.asDomainModel(): User {
    return User(
        accountNo = accountNo,
        token = token,
        username = username,
    )
}
