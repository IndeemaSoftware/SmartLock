package com.indeema.smartlock.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.indeema.smartlock.R
import com.indeema.smartlock.contract.BaseActivityContract
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:41
 */

abstract class BaseActivity<T : BaseActivityContract.BaseActivityActions> :
        AppCompatActivity(),
        BaseActivityContract.BaseActivityViews {

    protected val TAG = this.javaClass.simpleName

    @Inject
    protected lateinit var mActions: T

    private var mDisableTouches: Boolean = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(R.layout.activity_base)
        layoutInflater.inflate(layoutResID, base_activity_container)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mActions.onViewAttached(this)
    }

    override fun onDetachedFromWindow() {
        mActions.onViewDetached()
        super.onDetachedFromWindow()
    }

    override fun onDestroy() {
        mActions.onViewDestroyed()
        super.onDestroy()
    }

    override fun showLoading() {
        Log.d(TAG, "showLoading ----> Show")
        if (window != null && window.decorView != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        }
        mDisableTouches = true
        base_activity_progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        Log.d(TAG, "hideLoading ----> Hide")
        mDisableTouches = false
        base_activity_progress_bar.visibility = View.GONE
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return !mDisableTouches && super.dispatchTouchEvent(ev)
    }
}