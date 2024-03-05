package com.example.restream.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restream.R
import com.example.restream.TAG
import com.example.restream.databinding.ActivityRegistrationBinding
import com.example.restream.retrofit.ApiService
import com.example.restream.retrofit.PostDataSignUp
import com.example.restream.retrofit.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
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


@SuppressLint("SuspiciousIndentation")
class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val _response = MutableLiveData<Int>()
    val response: LiveData<Int>
        get() = _response


    fun registrUserRequest(binding: ActivityRegistrationBinding) {
        val signUp = User(
            binding.email.text.toString(),
            binding.pass.text.toString(),
            binding.confirmPass.text.toString()
        )
        val postDataSignUp = PostDataSignUp(signUp, null, null)

        viewModelScope.launch {
            try {
                val userResponse = apiService.signUp(postDataSignUp)

                val statusCode = userResponse.code()

                _response.value = statusCode


            } catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса!!!! ${e.printStackTrace()}")
                binding.registerbtn.setText(R.string.wait)
                _response.value = 0

            }


        }


    }


}