package com.indeema.smartlock.contract


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:48
 */

class AuthContract : BaseActivityContract() {

    interface Actions : BaseActivityActions {
        fun checkApiKey()

        fun checkCredentials(userName: String, password: String)
    }

    interface Views : BaseActivityViews {
        fun userNameInvalid()

        fun passwordInvalid()

        fun loginError(error: Throwable)

        fun loginSuccess()
    }
}