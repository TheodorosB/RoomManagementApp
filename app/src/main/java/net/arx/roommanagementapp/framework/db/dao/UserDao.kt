package net.arx.roommanagementapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.arx.roommanagementapp.framework.db.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("SELECT * FROM users WHERE password = :password LIMIT 1")
    suspend fun login(password: String): UserEntity?

    @Query("SELECT * FROM users WHERE role = 'CLEANER'")
    suspend fun getAllCleaners(): List<UserEntity>
}
