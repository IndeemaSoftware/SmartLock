package com.indeema.domain.interactor

import android.content.Context
import com.indeema.domain.action.Complete
import com.indeema.domain.action.Error
import com.indeema.domain.action.Next
import com.indeema.domain.interactor.interfaces.BaseInteractor
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 17:14
 */

abstract class BaseInteractorImpl (appContext: Context) : BaseInteractor {

    protected val TAG = this.javaClass.simpleName

    protected var mAppContext: Context = appContext
    protected var mDisposables: MutableList<Disposable> = ArrayList()

    private fun addDisposable(disposable: Disposable) {
        mDisposables.add(disposable)
    }

    override fun release() {
        mDisposables
                .filterNot { it.isDisposed }
                .forEach { it.dispose() }
        mDisposables.clear()
    }


    protected fun <T> createObserver(next: Next<T>, error: Error): Observer<T> {
        return createObserver(next, error, null)
    }

    protected fun <T> createObserver(next: Next<T>, error: Error, complete: Complete?): Observer<T> {
        return object : Observer<T> {
            override fun onSubscribe(d: Disposable) {
                addDisposable(d)
            }

            override fun onNext(data: T) {
                next.onNext(data)
            }

            override fun onError(t: Throwable) {
                error.onError(t)
            }

            override fun onComplete() {
                complete?.onComplete()
            }
        }
    }
}
