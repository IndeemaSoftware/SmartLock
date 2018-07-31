package com.indeema.domain.interactor

import android.content.Context
import com.indeema.data.entities.DoorRequest
import com.indeema.data.entities.DoorResponce
import com.indeema.data.usecase.DoorUseCase
import com.indeema.domain.action.Error
import com.indeema.domain.action.Next
import com.indeema.domain.interactor.interfaces.DoorInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:21
 */

class DoorInteractorImpl(mContext: Context, private val mDoorUseCase: DoorUseCase)
    : BaseInteractorImpl(mContext), DoorInteractor {

    override fun openDoor(next: Next<DoorResponce>, error: Error, doorRequest:  DoorRequest) {
        mDoorUseCase.openDoor(doorRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createObserver(next, error))
    }
}