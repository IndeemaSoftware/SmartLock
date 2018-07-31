package com.indeema.smartlock.presenter

import android.content.Context
import com.indeema.smartlock.contract.BaseActivityContract


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 17:04
 */

abstract class BaseActivityPresenter<V : BaseActivityContract.BaseActivityViews>(context: Context) : BasePresenter<V>(context),
        BaseActivityContract.BaseActivityActions