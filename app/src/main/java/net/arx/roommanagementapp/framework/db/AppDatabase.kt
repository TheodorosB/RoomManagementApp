package net.arx.roommanagementapp.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.arx.roommanagementapp.framework.db.dao.RoomDao
import net.arx.roommanagementapp.framework.db.dao.UserDao
import net.arx.roommanagementapp.framework.db.entity.RoomEntity
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.util.converters.Converters

@Database(
    entities = [RoomEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
    //abstract fun cleaningTaskDao(): CleaningTaskDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
    }
}