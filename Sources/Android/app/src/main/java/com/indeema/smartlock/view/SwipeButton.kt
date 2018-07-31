package com.indeema.smartlock.view

import android.widget.RelativeLayout


import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.view.MotionEvent
import android.view.View
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.animation.ObjectAnimator
import android.support.v4.content.ContextCompat.*
import android.util.Log
import android.util.Log.i
import android.view.animation.AccelerateDecelerateInterpolator
import butterknife.BindView
import butterknife.ButterKnife
import com.indeema.smartlock.R


/**
 * @author Ruslan Stosyk
 * Date: May, 21, 2018
 * Time: 0:26
 */

class SwipeButton : RelativeLayout {

    interface OnSwipeListener {
        fun onActive()
    }

    private lateinit var mListener: OnSwipeListener
    @BindView(R.id.swipe_button_button)
    lateinit var mSlidingButton: ImageView
    @BindView(R.id.swipe_button_view_lock)
    lateinit var mViewLocked: View

    private var mInitialX: Float = 0f
    private var mActive: Boolean = false

    private lateinit var mDisabledDrawable: Drawable

    private lateinit var mEnabledDrawable: Drawable

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1, defStyleRes: Int = -1) {
        val view = View.inflate(context, R.layout.swipe_button, this)
        ButterKnife.bind(view)
        mDisabledDrawable = getDrawable(getContext(), R.drawable.ic_lock)!!
        mEnabledDrawable = getDrawable(getContext(), R.drawable.ic_unlock)!!
        setOnTouchListener(getButtonTouchListener())
    }

    private fun getButtonTouchListener(): View.OnTouchListener {
        return OnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> return@OnTouchListener true
                MotionEvent.ACTION_MOVE -> {
                    if (mInitialX == 0f) {
                        mInitialX = mSlidingButton.x
                    }
                    if (event.x > mInitialX + mSlidingButton.width / 2 &&
                            event.x + mSlidingButton.width / 2 < width) {
                        mSlidingButton.x = event.x - mSlidingButton.width / 2

                        mViewLocked.alpha = 1 - 1.3f * (mSlidingButton.x + mSlidingButton.width) / width
                    //    Log.d("TEST", "first " + event.x)
                    }
                    if (event.x + mSlidingButton.width / 2 > width &&
                            mSlidingButton.x + mSlidingButton.width / 2 < width) {
                        mSlidingButton.x = (width - mSlidingButton.width).toFloat()
                        mViewLocked.alpha = 1 - 1.3f * (mSlidingButton.x + mSlidingButton.width) / width
                                // Log.d("TEST", "second" + event.x)
                    }

                    if (event.x < mSlidingButton.width / 2 && mSlidingButton.x > 0) {
                        mSlidingButton.x = 0f
                      //  Log.d("TEST", "third" + event.x)

                    }
                    return@OnTouchListener true
                }

                MotionEvent.ACTION_UP -> {
                    if (mActive) {
                        moveButtonBack()
                    } else {
                        if (mSlidingButton.x + mSlidingButton.width > width * 0.85) {
                            expandButton()
                        } else {
                            moveButtonBack()
                        }
                    }
                    return@OnTouchListener true
                }
            }
            false
        }
    }

    private fun expandButton() {
        val positionAnimator = ValueAnimator.ofFloat(mSlidingButton.x, (width - mSlidingButton.width).toFloat())
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            mSlidingButton.x = x
        }
        positionAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                mActive = true
                mSlidingButton.setImageDrawable(mEnabledDrawable)
                mListener.onActive()
            }
        })
        positionAnimator.duration = 80
        positionAnimator.start()
    }

    override fun setClickable(canClick: Boolean) {
        if (canClick) setOnTouchListener(getButtonTouchListener())
        else setOnTouchListener(null)
    }

    fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(mSlidingButton.x, 0f)
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            mSlidingButton.x = x
        }
        val objectAnimator = ObjectAnimator.ofFloat(
                mViewLocked, "alpha", 1f)
        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                mActive = false
                mSlidingButton.setImageDrawable(mDisabledDrawable)
            }
        })
        animatorSet.playTogether(objectAnimator, positionAnimator)
        animatorSet.duration = 200
        animatorSet.start()
    }

    fun setOnSwipedListener(listener: OnSwipeListener) {
        mListener = listener
    }
}