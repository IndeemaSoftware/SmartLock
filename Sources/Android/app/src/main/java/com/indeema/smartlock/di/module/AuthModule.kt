package com.indeema.smartlock.di.module

import android.content.Context
import com.indeema.domain.interactor.interfaces.AuthInteractor
import com.indeema.smartlock.activity.AuthActivity
import com.indeema.smartlock.contract.AuthContract
import com.indeema.smartlock.di.scope.AuthScope
import com.indeema.smartlock.presenter.AuthPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:14
 */

@Module
class AuthModule {

    @Provides
    @AuthScope
    fun provideAuthActivityAction(context: Context, mAuthInteractor: AuthInteractor): AuthContract.Actions {
        return AuthPresenter(context, mAuthInteractor)
    }
}