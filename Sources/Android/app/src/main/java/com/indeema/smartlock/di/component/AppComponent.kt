package com.indeema.smartlock.di.component

import com.indeema.smartlock.App
import com.indeema.smartlock.di.module.*
import dagger.Component
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:10
 */

@Singleton
@Component(modules = [AppModule::class, UseCaseModule::class, InteractorModule::class, RedmineApiModule::class, DoorApiModule::class])
interface AppComponent {
    fun inject(app: App)

    fun initAuthComponent(homeModule: AuthModule): AuthComponent

    fun initDoorComponent(homeModule: DoorModule): DoorComponent
    fun initSettingsComponent(settingsModule: SettingsModule): SettingsComponent
}