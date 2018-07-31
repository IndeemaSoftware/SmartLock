package com.indeema.smartlock.extention

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 12:59
 */


fun AppCompatActivity.toast(@StringRes stringIdRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringIdRes, duration).show()
}