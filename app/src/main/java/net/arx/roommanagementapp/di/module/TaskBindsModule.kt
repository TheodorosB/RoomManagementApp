package net.arx.roommanagementapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.arx.roommanagementapp.data.task.RoomStatusDataSource
import net.arx.roommanagementapp.data.task.RoomStatusRepositoryImpl
import net.arx.roommanagementapp.domain.status.RoomStatusRepository
import net.arx.roommanagementapp.framework.status.RoomStatusDataSourceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
interface TaskBindsModule {

    @Binds
    fun bindTaskDataSource(dataSource: RoomStatusDataSourceImpl): RoomStatusDataSource

    @Binds
    fun bindTaskRepository(repository: RoomStatusRepositoryImpl): RoomStatusRepository
}
