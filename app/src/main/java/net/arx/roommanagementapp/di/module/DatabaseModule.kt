package net.arx.roommanagementapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.arx.roommanagementapp.framework.db.AppDatabase
import net.arx.roommanagementapp.framework.db.dao.RoomStatusDao
import net.arx.roommanagementapp.framework.db.dao.RoomDao
import net.arx.roommanagementapp.framework.db.dao.UserDao
import net.arx.roommanagementapp.framework.db.entity.UserEntity
import net.arx.roommanagementapp.framework.db.entity.UserRole
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "room_management_db"
        )
        .build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideRoomDao(db: AppDatabase): RoomDao = db.roomDao()

    @Provides
    fun provideCleaningTaskDao(db: AppDatabase): RoomStatusDao = db.cleaningTaskDao()
}