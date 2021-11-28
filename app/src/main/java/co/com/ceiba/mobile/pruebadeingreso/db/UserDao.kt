package co.com.ceiba.mobile.pruebadeingreso.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg user: User)
}