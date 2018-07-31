package com.indeema.smartlock.di.module

import android.content.Context
import com.indeema.domain.interactor.interfaces.AuthInteractor
import com.indeema.domain.interactor.interfaces.DoorInteractor
import com.indeema.smartlock.contract.AuthContract
import com.indeema.smartlock.contract.DoorContract
import com.indeema.smartlock.di.scope.AuthScope
import com.indeema.smartlock.di.scope.DoorScope
import com.indeema.smartlock.presenter.AuthPresenter
import com.indeema.smartlock.presenter.DoorPresenter
import dagger.Module
import dagger.Provides


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:25
 */

@Module
class DoorModule {

    @Provides
    @DoorScope
    fun provideDoorActivityAction(context: Context, mDoorInteractor: DoorInteractor): DoorContract.Actions {
        return DoorPresenter(context, mDoorInteractor)
    }
}