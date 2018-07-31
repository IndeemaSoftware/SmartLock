package com.indeema.smartlock.presenter

import android.content.Context
import android.text.TextUtils
import android.util.Base64.*
import com.indeema.data.entities.RedmineResponce
import com.indeema.data.entities.User
import com.indeema.domain.action.Error
import com.indeema.domain.action.Next
import com.indeema.domain.interactor.interfaces.AuthInteractor
import com.indeema.smartlock.contract.AuthContract.Actions
import com.indeema.smartlock.contract.AuthContract.Views
import com.indeema.smartlock.extention.PreferenceHelper


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 17:05
 */

class AuthPresenter(context: Context, val mAuthInteractor: AuthInteractor) : BaseActivityPresenter<Views>(context), Actions {


    override fun checkApiKey() {
        if (PreferenceHelper.getApiKey(mAppContext) != "")
            mView.loginSuccess()
        else return
    }

    override fun checkCredentials(userName: String, password: String) {
        when {
            TextUtils.isEmpty(userName) -> mView.userNameInvalid()
            TextUtils.isEmpty(password) -> mView.passwordInvalid()
            !TextUtils.isEmpty(userName) -> startRequest(userName, password)
        }
    }

    private fun startRequest(userName: String, password: String) {
        mView.showLoading()
        val base = "$userName:$password"
        val credential: String = "Basic " + (String(encode(base.toByteArray(), DEFAULT))).trim()
        mAuthInteractor.login(object : Next<RedmineResponce> {
            override fun onNext(data: RedmineResponce) {
                mView.hideLoading()
                data.user?.let { saveInformation(it) }
                mView.loginSuccess()
            }
        }, object : Error {
            override fun onError(throwable: Throwable) {
                mView.hideLoading()
                mView.loginError(throwable)
            }
        }, credential)
    }

    private fun saveInformation(user: User) {
        PreferenceHelper.setApiKey(mAppContext, "${user.apiKey}")
        PreferenceHelper.setUserName(mAppContext, "${user.firstname} ${user.lastname}")
    }

    override fun release() {
        releaseInteractor()
    }
}
