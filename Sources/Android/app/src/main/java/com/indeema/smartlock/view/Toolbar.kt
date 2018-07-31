package com.indeema.smartlock.view

import android.content.Context
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.indeema.smartlock.R


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 15:14
 */

class Toolbar : RelativeLayout {


    @BindView(R.id.toolbar_title)
    lateinit var mTitleTV: TextView
    @BindView(R.id.toolbar_back_btn)
    lateinit var mBackIB: ImageButton

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val view = View.inflate(context, R.layout.toolbar, this)
        ButterKnife.bind(view)
    }

    fun setTitleText(@StringRes title: Int) {
        mTitleTV.setText(title)
    }

    fun setTitleText(title: String) {
        mTitleTV.text = title
    }

    fun setVisibilityBackBtn(visibility: Int) {
        mBackIB.visibility = visibility
    }

    fun setOnBackClickListener(onBackClickListener: View.OnClickListener) {
        mBackIB.setOnClickListener(onBackClickListener)
    }
}
