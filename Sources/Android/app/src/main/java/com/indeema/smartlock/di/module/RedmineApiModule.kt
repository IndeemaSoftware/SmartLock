package com.indeema.smartlock.di.module

import com.indeema.data.serices.RedmineApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 19:49
 */

@Module
class RedmineApiModule(var mDomain: String) {


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        // set your desired log level
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideRestOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideRestRedmineApiService(okHttpClient: OkHttpClient): RedmineApiService {
        return Retrofit.Builder()
                .baseUrl(mDomain)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RedmineApiService::class.java)
    }
}
