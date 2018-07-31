package com.indeema.smartlock.di.module

import com.indeema.data.serices.DoorApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 0:05
 */

@Module
class DoorApiModule(var mDomain: String) {

    @Provides
    @Singleton
    fun provideRestRedmineApiService(okHttpClient: OkHttpClient): DoorApiService {
        return Retrofit.Builder()
                .baseUrl(mDomain)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(DoorApiService::class.java)
    }
}
