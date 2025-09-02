package net.arx.roommanagementapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.arx.roommanagementapp.data.user.UserDataSource
import net.arx.roommanagementapp.data.user.UserRepositoryImpl
import net.arx.roommanagementapp.domain.user.UserRepository
import net.arx.roommanagementapp.framework.user.UserDataSourceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UserBindsModule {

    @Binds
    fun bindUserDataSource(dataSource: UserDataSourceImpl): UserDataSource

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
