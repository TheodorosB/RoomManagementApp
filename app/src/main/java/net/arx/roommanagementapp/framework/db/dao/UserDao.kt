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

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun usernameExists(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE password = :password LIMIT 1")
    suspend fun passwordExists(password: String): UserEntity?

    @Query("SELECT * FROM users WHERE password = :password LIMIT 1")
    suspend fun login(password: String): UserEntity?
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUser(id: Long): UserEntity?

    @Query("SELECT * FROM users WHERE role = 'USER'")
    suspend fun getAllCleaners(): List<UserEntity>
}
