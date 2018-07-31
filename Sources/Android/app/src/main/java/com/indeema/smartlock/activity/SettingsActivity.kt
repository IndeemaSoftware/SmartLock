package com.indeema.smartlock.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.indeema.smartlock.App
import com.indeema.smartlock.R
import com.indeema.smartlock.view.Toolbar
import com.indeema.smartlock.contract.SettingsContract.Actions
import com.indeema.smartlock.contract.SettingsContract.Views
import com.indeema.smartlock.extention.PreferenceHelper


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 15:01
 */

class SettingsActivity : BaseActivity<Actions>(), Views {

    private lateinit var mUnbinder: Unbinder

    @BindView(R.id.settings_activity_toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.settings_activity_profile_name)
    lateinit var mProfileNameTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).getSettingsComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        mUnbinder = ButterKnife.bind(this)
        mToolbar.setTitleText(R.string.settings_activity_settings)
        mToolbar.setOnBackClickListener(View.OnClickListener {
            onBackPressed()
        })
        mProfileNameTV.text = PreferenceHelper.getUserName(this)
    }

    @OnClick(R.id.settings_activity_log_out_container)
    fun onClick() {
        PreferenceHelper.setApiKey(this, "")
        PreferenceHelper.setUserName(this, "")
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseSettingsComponetn()
        mUnbinder.unbind()
    }
}