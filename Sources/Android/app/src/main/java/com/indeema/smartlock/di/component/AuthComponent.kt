package com.indeema.smartlock.di.component

import com.indeema.smartlock.activity.AuthActivity
import com.indeema.smartlock.di.module.AuthModule
import com.indeema.smartlock.di.scope.AuthScope
import dagger.Subcomponent
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:18
 */

@Subcomponent(modules = [(AuthModule::class)])
@AuthScope
interface AuthComponent {
    fun inject(activity: AuthActivity)
}