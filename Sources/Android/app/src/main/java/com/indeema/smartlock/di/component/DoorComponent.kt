package com.indeema.smartlock.di.component

import com.indeema.smartlock.activity.AuthActivity
import com.indeema.smartlock.activity.DoorActivity
import com.indeema.smartlock.di.module.AuthModule
import com.indeema.smartlock.di.module.DoorModule
import com.indeema.smartlock.di.scope.AuthScope
import com.indeema.smartlock.di.scope.DoorScope
import dagger.Subcomponent


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:29
 */

@Subcomponent(modules = [(DoorModule::class)])
@DoorScope
interface DoorComponent {
    fun inject(activity: DoorActivity)
}