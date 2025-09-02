package net.arx.roommanagementapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.arx.roommanagementapp.data.room.RoomDataSource
import net.arx.roommanagementapp.data.room.RoomRepositoryImpl
import net.arx.roommanagementapp.domain.room.RoomRepository
import net.arx.roommanagementapp.framework.room.RoomDataSourceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RoomBindsModule {

    @Binds
    fun bindRoomDataSource(dataSource: RoomDataSourceImpl): RoomDataSource

    @Binds
    fun bindRoomRepository(repository: RoomRepositoryImpl): RoomRepository
}