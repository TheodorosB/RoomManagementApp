package net.arx.roommanagementapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.arx.roommanagementapp.data.task.TaskDataSource
import net.arx.roommanagementapp.data.task.TaskRepositoryImpl
import net.arx.roommanagementapp.domain.task.TaskRepository
import net.arx.roommanagementapp.framework.task.TaskDataSourceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
interface TaskBindsModule {

    @Binds
    fun bindTaskDataSource(dataSource: TaskDataSourceImpl): TaskDataSource

    @Binds
    fun bindTaskRepository(repository: TaskRepositoryImpl): TaskRepository
}
