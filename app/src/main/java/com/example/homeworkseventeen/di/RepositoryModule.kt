package com.example.homeworkseventeen.di

import com.example.homeworkseventeen.data.log_in.LogInRepositoryImpl
import com.example.homeworkseventeen.data.log_in.LogInService
import com.example.homeworkseventeen.data.register.RegisterRepositoryImpl
import com.example.homeworkseventeen.data.register.RegisterService
import com.example.homeworkseventeen.domain.log_in.LogInRepository
import com.example.homeworkseventeen.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun  provideLoginRepository(logInService: LogInService): LogInRepository{
        return LogInRepositoryImpl(logInService)
    }

    @Singleton
    @Provides
    fun  provideRegisterRepository(registerService: RegisterService): RegisterRepository{
        return RegisterRepositoryImpl(registerService)
    }
}