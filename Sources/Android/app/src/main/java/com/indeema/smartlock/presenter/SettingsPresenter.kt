package com.indeema.smartlock.presenter

import android.content.Context
import com.indeema.domain.interactor.interfaces.DoorInteractor
import com.indeema.smartlock.contract.SettingsContract.Actions
import com.indeema.smartlock.contract.SettingsContract.Views


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 15:02
 */

class SettingsPresenter(mContext: Context) : BaseActivityPresenter<Views>(mContext), Actions {

    override fun release() {

    }
}