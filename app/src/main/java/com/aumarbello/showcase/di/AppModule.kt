package com.aumarbello.showcase.di

import android.content.Context
import com.aumarbello.showcase.BuildConfig
import com.aumarbello.showcase.data.api.ShowcaseService
import com.aumarbello.showcase.data.preferences.AppPreferences
import com.aumarbello.showcase.data.preferences.ShowcasePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }

        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl("https://api.staging.myautochek.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesShowcaseService(retrofit: Retrofit): ShowcaseService {
        return retrofit.create(ShowcaseService::class.java)
    }

    @Provides
    @Singleton
    fun providesPreference(@ApplicationContext context: Context): ShowcasePreferences {
        return AppPreferences(context)
    }
}