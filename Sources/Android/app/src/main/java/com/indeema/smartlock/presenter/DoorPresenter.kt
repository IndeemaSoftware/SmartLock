package com.indeema.smartlock.presenter

import android.content.Context
import com.indeema.data.entities.DoorRequest
import com.indeema.data.entities.DoorResponce
import com.indeema.domain.action.Error
import com.indeema.domain.action.Next
import com.indeema.domain.interactor.interfaces.DoorInteractor
import com.indeema.smartlock.contract.DoorContract.Actions
import com.indeema.smartlock.contract.DoorContract.Views
import com.indeema.smartlock.extention.PreferenceHelper


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:03
 */

class DoorPresenter(mContext: Context, val mDoorInteractor: DoorInteractor) : BaseActivityPresenter<Views>(mContext), Actions {

    override fun openDoor() {
        mView.showLoading()
        val apiKey: String = PreferenceHelper.getApiKey(mAppContext)
        val doorRequest = DoorRequest()
        doorRequest.apiKey = apiKey
        mDoorInteractor.openDoor(object : Next<DoorResponce> {
            override fun onNext(data: DoorResponce) {
                mView.hideLoading()
                mView.successOpeningDoor()
            }
        }, object : Error{
            override fun onError(throwable: Throwable) {
                mView.hideLoading()
                mView.errorOpenDoor(throwable)
            }
        }, doorRequest)
    }

    override fun release() {
        releaseInteractor()
    }
}