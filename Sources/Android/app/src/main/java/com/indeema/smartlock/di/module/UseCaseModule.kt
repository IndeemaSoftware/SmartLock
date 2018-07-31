package com.indeema.smartlock.di.module

import com.indeema.data.serices.DoorApiService
import com.indeema.data.serices.RedmineApiService
import com.indeema.data.usecase.AuthUseCase
import com.indeema.data.usecase.DoorUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 20:51
 */

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideAuthUseCase(redmineApiService: RedmineApiService): AuthUseCase {
        return AuthUseCase(redmineApiService)
    }

    @Provides
    @Singleton
    fun provideDoorUseCase(doorApiService: DoorApiService): DoorUseCase {
        return DoorUseCase(doorApiService)
    }
}