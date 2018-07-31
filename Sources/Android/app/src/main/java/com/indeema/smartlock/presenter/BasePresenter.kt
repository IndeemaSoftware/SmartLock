package com.indeema.smartlock.presenter

import android.content.Context
import android.support.annotation.StringRes
import android.util.Log
import com.indeema.domain.interactor.interfaces.BaseInteractor
import com.indeema.smartlock.contract.BaseContract


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 17:03
 */

@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V : BaseContract.BaseViews>(context: Context) : BaseContract.BaseActions {
    protected val TAG = this.javaClass.simpleName

    protected var mAppContext: Context = context
    protected lateinit var mView: V

    override fun <View : BaseContract.BaseViews> onViewAttached(view: View) {
        Log.d(TAG, "onViewAttached ----> Was Attached")
        mView = view as V
    }

    override fun onViewDetached() {
        Log.d(TAG, "onViewDetached -----> view was detached")
    }

    override fun onViewDestroyed() {
        Log.d(TAG, "onViewDestroyed ----> view was destroyed")
        release()
    }

    fun releaseInteractor(vararg interactors: BaseInteractor) {
        for (interactor in interactors) {
            interactor.release()
        }
    }
}