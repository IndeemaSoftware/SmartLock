package com.indeema.smartlock.di.module

import android.content.Context
import com.indeema.domain.interactor.interfaces.DoorInteractor
import com.indeema.smartlock.contract.DoorContract
import com.indeema.smartlock.contract.SettingsContract
import com.indeema.smartlock.di.scope.DoorScope
import com.indeema.smartlock.di.scope.SettingsScope
import com.indeema.smartlock.presenter.DoorPresenter
import com.indeema.smartlock.presenter.SettingsPresenter
import dagger.Module
import dagger.Provides


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 15:09
 */

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideSettingsActivityAction(context: Context): SettingsContract.Actions {
        return SettingsPresenter(context)
    }
}