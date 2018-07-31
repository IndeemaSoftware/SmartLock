package com.indeema.smartlock.contract


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:39
 */

abstract class BaseContract {

    interface BaseActions {
        fun <View : BaseViews> onViewAttached(view: View)

        fun onViewDetached()

        fun onViewDestroyed()

        fun release()
    }

    interface BaseViews {

        fun showLoading()

        fun hideLoading()
    }
}