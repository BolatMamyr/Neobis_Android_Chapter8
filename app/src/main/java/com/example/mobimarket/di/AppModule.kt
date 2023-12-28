package com.example.mobimarket.di

import android.content.Context
import android.content.SharedPreferences
import com.example.mobimarket.api.Api
import com.example.mobimarket.managers.UserManager
import com.example.mobimarket.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getRetrofit(userManager: UserManager): Api {
        val gson = GsonBuilder().setLenient().create()
        val token = userManager.accessToken

        // Interceptor для добавления токена авторизации
        val interceptor = Interceptor { chain ->
            val newRequest =
                chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
            chain.proceed(newRequest)
        }

        // Interceptor для логирования
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor).build()

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun getSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("MobiMarketSharedPrefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun getUserManager(pref: SharedPreferences) = UserManager(pref)
}