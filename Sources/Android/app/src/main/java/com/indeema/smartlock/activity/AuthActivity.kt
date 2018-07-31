package com.indeema.smartlock.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.indeema.smartlock.App
import com.indeema.smartlock.R
import com.indeema.smartlock.contract.AuthContract.Actions
import com.indeema.smartlock.contract.AuthContract.Views
import com.indeema.smartlock.extention.toast

class AuthActivity : BaseActivity<Actions>(), Views {
    @BindView(R.id.auth_activity_user_name_et)
    lateinit var mUserNameField: EditText

    @BindView(R.id.auth_activity_password_et)
    lateinit var mPasswordField: EditText


    private lateinit var mUnbinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).getAuthComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mUnbinder = ButterKnife.bind(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mActions.checkApiKey()
    }

    @OnClick(R.id.auth_activity_login_btn)
    fun onClick() {
        val userName: String = mUserNameField.text.toString()
        val password: String = mPasswordField.text.toString()
        mActions.checkCredentials(userName, password)
    }

    override fun userNameInvalid() {
        mUserNameField.error = getString(R.string.auth_activity_name_invalid)
    }

    override fun passwordInvalid() {
        mPasswordField.error = getString(R.string.auth_activity_password_invalid)
    }

    override fun loginSuccess() {
        val intent = Intent(this, DoorActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun loginError(error: Throwable) {
        this.toast(R.string.auth_activity_toast_error)
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseAuthComponent()
        mUnbinder.unbind()
    }
}
