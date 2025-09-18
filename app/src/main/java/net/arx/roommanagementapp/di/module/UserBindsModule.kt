package net.arx.roommanagementapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.arx.roommanagementapp.data.user.datasource.UserDataSource
import net.arx.roommanagementapp.data.user.repository.UserRepositoryImpl
import net.arx.roommanagementapp.domain.user.repository.UserRepository
import net.arx.roommanagementapp.framework.user.datasource.UserDataSourceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UserBindsModule {

    @Binds
    fun bindUserDataSource(dataSource: UserDataSourceImpl): UserDataSource

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
