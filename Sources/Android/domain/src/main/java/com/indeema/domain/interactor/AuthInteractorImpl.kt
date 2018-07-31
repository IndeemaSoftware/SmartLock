package com.indeema.domain.interactor

import android.content.Context
import com.indeema.data.entities.RedmineResponce
import com.indeema.data.usecase.AuthUseCase
import com.indeema.domain.action.Error
import com.indeema.domain.action.Next
import com.indeema.domain.interactor.interfaces.AuthInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 20:44
 */

class AuthInteractorImpl(mContext: Context, private val mAuthUseCase: AuthUseCase)
    : BaseInteractorImpl(mContext), AuthInteractor {


    override fun login(next: Next<RedmineResponce>, error: Error, credential: String) {
        mAuthUseCase.login(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createObserver(next, error))
    }
}