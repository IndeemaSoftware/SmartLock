package com.indeema.smartlock.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.indeema.smartlock.App
import com.indeema.smartlock.R
import com.indeema.smartlock.contract.DoorContract
import android.animation.ValueAnimator
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import butterknife.OnClick
import com.indeema.smartlock.extention.PreferenceHelper
import com.indeema.smartlock.extention.toast
import com.indeema.smartlock.view.SwipeButton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:01
 */

class DoorActivity : BaseActivity<DoorContract.Actions>(), DoorContract.Views {
    @BindView(R.id.door_activity_switch)
    lateinit var mSwitch: SwipeButton


    @BindView(R.id.door_activity_progress_bar)
    lateinit var mProgressBar: ProgressBar

    @BindView(R.id.door_activity_second_tv)
    lateinit var mSecondsTV: TextView
    private lateinit var mUnbinder: Unbinder

    fun start(activity: Activity) {
        val intent = Intent(activity, DoorActivity::class.java)
        activity.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).getDoorComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door)
        mUnbinder = ButterKnife.bind(this)

        mSwitch.setOnSwipedListener(object : SwipeButton.OnSwipeListener {
            override fun onActive() {
                mActions.openDoor()
            }
        })

    }

    override fun onStart() {
        super.onStart()
        if (PreferenceHelper.getApiKey(this) == "")
            finish()
    }

    @OnClick(R.id.default_activity_settings)
    fun onClick() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun successOpeningDoor() {
        switchViews(true)
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 2000
        animator.addUpdateListener { animation ->
            animation(animation)
        }
        animator.start()
    }

    override fun errorOpenDoor(throwable: Throwable) {
        toast(R.string.door_activity_error)
        switchViews(false)
    }

    private fun animation(animator: ValueAnimator) {
        val progress: Int = animator.animatedValue as Int
        mProgressBar.progress = progress
        val percentage: Int = 100 - progress
        val time: Double = ((2000 / 100 * percentage).toDouble())
        val seconds: Double = time / 1000
        mSecondsTV.text = getString(R.string.door_activity_sec, seconds.toString().substring(0, 3))
        if (progress == 100) {
            switchViews(false)
        }
    }

    override fun switchViews(isOpen: Boolean) {
        mSwitch.isClickable = !isOpen
        mSecondsTV.visibility = if (isOpen) VISIBLE else GONE
        mProgressBar.visibility = if (isOpen) VISIBLE else GONE
        if (!isOpen) mSwitch.moveButtonBack()
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseSettingsComponetn()
        mUnbinder.unbind()
    }
}
