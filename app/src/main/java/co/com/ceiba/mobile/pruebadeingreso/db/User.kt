package co.com.ceiba.mobile.pruebadeingreso.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(
    val name: String,
    val phone: String,
    val email: String,
    @PrimaryKey
    val id: Int
)