package net.arx.roommanagementapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.arx.roommanagementapp.data.status.datasource.RoomStatusDataSource
import net.arx.roommanagementapp.data.status.repository.RoomStatusRepositoryImpl
import net.arx.roommanagementapp.domain.status.repository.RoomStatusRepository
import net.arx.roommanagementapp.framework.status.datasource.RoomStatusDataSourceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
interface TaskBindsModule {

    @Binds
    fun bindTaskDataSource(dataSource: RoomStatusDataSourceImpl): RoomStatusDataSource

    @Binds
    fun bindTaskRepository(repository: RoomStatusRepositoryImpl): RoomStatusRepository
}
