package com.example.restream.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restream.TAG
import com.example.restream.databinding.ActivityAuthorizationBinding
import com.example.restream.retrofit.ApiService
import com.example.restream.retrofit.PostDataSignIn
import com.example.restream.retrofit.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookiePolicy

val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.d(
            TAG,
            "SERVER_RESPONSE ${message}"
        ) // Устанавливаем тег для логирования ответов сервера
    }
}).apply {
    level = HttpLoggingInterceptor.Level.BODY
}


val cookieManager = java.net.CookieManager().apply {
    setCookiePolicy(CookiePolicy.ACCEPT_ALL)
}


val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .cookieJar(JavaNetCookieJar(cookieManager))
    .build()

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val converterFactory = MoshiConverterFactory.create(moshi)
val retrofit = Retrofit.Builder()
    .baseUrl("https://app.restream.su/")
    .client(client)
    .addConverterFactory(converterFactory)
    .build()
val apiService = retrofit.create(ApiService::class.java)

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {



    private val _response = MutableLiveData<Int>()
    val response: LiveData<Int>
        get() = _response

    private val _responseUser = MutableLiveData<String>()
    val responseUser: LiveData<String>
        get() = _responseUser

    val userListLiveData = MutableLiveData<List<String>>()




    fun authUserRequest(binding: ActivityAuthorizationBinding) {
        val user = User(
            binding.email.text.toString(),
            binding.pass.text.toString()
        )
        val postDataSignIn = PostDataSignIn(user)



        viewModelScope.launch {
            try {
                val userResponse = apiService.signIn(postDataSignIn)

                val statusCode = userResponse.code()

                _response.value = statusCode
            } catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса авторизации!!!! ${e.printStackTrace()}")
                _response.value = 0

            }
        }
    }


    fun checkUser() {

        val userList = mutableListOf<String>()

        viewModelScope.launch {
            try {
                val userResponse = apiService.user()

                val userEmail = userResponse.user_email
                val regdate = userResponse.registration_date
                val userTariff = userResponse.tariff!!.name

                userList.add(userEmail.toString())
                userList.add(regdate.toString())
                userList.add(userTariff)

                Log.d(TAG, "userEmail=${userEmail}")

                //   _responseUser.value = userEmail!!
                userListLiveData.value = userList


            } catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса проверки USER!!!! ${e.printStackTrace()}")
                // _responseUser.value = ""
                userListLiveData.value = listOf("")

            }


        }


    }


}