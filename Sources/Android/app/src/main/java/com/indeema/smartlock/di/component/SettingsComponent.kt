package com.indeema.smartlock.di.component

import com.indeema.smartlock.activity.DoorActivity
import com.indeema.smartlock.activity.SettingsActivity
import com.indeema.smartlock.di.module.DoorModule
import com.indeema.smartlock.di.module.SettingsModule
import com.indeema.smartlock.di.scope.DoorScope
import com.indeema.smartlock.di.scope.SettingsScope
import dagger.Subcomponent


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 15:08
 */

@Subcomponent(modules = [(SettingsModule::class)])
@SettingsScope

interface SettingsComponent {
    fun inject(activity: SettingsActivity)
}