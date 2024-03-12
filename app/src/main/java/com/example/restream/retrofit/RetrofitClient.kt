package com.example.restream.retrofit

import android.util.Log
import com.example.restream.TAG
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookiePolicy

object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d(
                TAG,
                "SERVER_RESPONSE ${message}"
            )
        }
    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val cookieManager = java.net.CookieManager().apply {
        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    }


    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .cookieJar(JavaNetCookieJar(cookieManager))
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val converterFactory = MoshiConverterFactory.create(moshi)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://app.restream.su/")
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
    val apiService = retrofit.create(ApiService::class.java)

}