package com.indeema.smartlock.di.module

import android.content.Context
import com.indeema.data.usecase.AuthUseCase
import com.indeema.data.usecase.DoorUseCase
import com.indeema.domain.interactor.AuthInteractorImpl
import com.indeema.domain.interactor.DoorInteractorImpl
import com.indeema.domain.interactor.interfaces.AuthInteractor
import com.indeema.domain.interactor.interfaces.DoorInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 20:49
 */

@Module
class InteractorModule {

    @Provides
    @Singleton
    internal fun provideAuthInteractor(context: Context, mAuthUseCase: AuthUseCase): AuthInteractor {
        return AuthInteractorImpl(context, mAuthUseCase)
    }

    @Provides
    @Singleton
    internal fun provideDoorInteractor(context: Context, mDoorUseCase: DoorUseCase): DoorInteractor {
        return DoorInteractorImpl(context, mDoorUseCase)
    }
}