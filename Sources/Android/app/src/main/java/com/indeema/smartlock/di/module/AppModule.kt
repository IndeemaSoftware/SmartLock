package com.indeema.smartlock.di.module

import android.content.Context
import com.indeema.smartlock.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 16:08
 */

@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun app(): App {
        return app
    }

    @Provides
    @Singleton
    fun context(): Context {
        return app.applicationContext
    }
}