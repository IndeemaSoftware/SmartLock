package com.indeema.smartlock

import android.app.Application
import com.indeema.smartlock.di.component.*
import com.indeema.smartlock.di.module.*


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:12
 */

class App : Application() {

    val REDMINE_DOMAIN = "https://redmine.{your_redmine_domain}.com"
    val DOOR_DOMAIN = "http://{your_server_endpoint}"

    private val mAppComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .redmineApiModule(RedmineApiModule(REDMINE_DOMAIN))
                .doorApiModule(DoorApiModule(DOOR_DOMAIN))
                .build()
    }

    private var mAuthActivityComponent: AuthComponent? = null
    private var mDoorActivityComponent: DoorComponent? = null
    private var mSettingsActivityComponent: SettingsComponent? = null

    override fun onCreate() {
        super.onCreate()
        mAppComponent.inject(this)
    }

    fun getAuthComponent(): AuthComponent {
        if (mAuthActivityComponent == null) {
            mAuthActivityComponent = mAppComponent.initAuthComponent(AuthModule())
        }
        return mAuthActivityComponent!!
    }

    fun getDoorComponent(): DoorComponent {
        if (mDoorActivityComponent == null) {
            mDoorActivityComponent = mAppComponent.initDoorComponent(DoorModule())
        }
        return mDoorActivityComponent!!
    }

    fun getSettingsComponent(): SettingsComponent {
        if (mSettingsActivityComponent == null) {
            mSettingsActivityComponent = mAppComponent.initSettingsComponent(SettingsModule())
        }
        return mSettingsActivityComponent!!
    }

    fun releaseAuthComponent() {
        mAuthActivityComponent = null
    }

    fun releaseDoorComponetn() {
        mDoorActivityComponent = null
    }

    fun releaseSettingsComponetn() {
        mSettingsActivityComponent = null
    }
}
