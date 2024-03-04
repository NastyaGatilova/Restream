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
import com.example.restream.retrofit.PostDataSignIn
import com.example.restream.retrofit.User
import kotlinx.coroutines.launch



class AuthorizationViewModel(application: Application): AndroidViewModel(application) {


    val sharedPreferences = application.getSharedPreferences("Cookies", Context.MODE_PRIVATE)

    private val _response = MutableLiveData<Int>()
    val response: LiveData<Int>
        get() = _response

    private val _responseUser = MutableLiveData<String>()
    val responseUser: LiveData<String>
        get() = _responseUser

    val userListLiveData = MutableLiveData<List<String>>()


    val cookieManager = CookieManager.getInstance()


    fun authUserRequest(binding: ActivityAuthorizationBinding){
        val user = User(
            binding.email.text.toString(),
            binding.pass.text.toString()
        )
        val postDataSignIn = PostDataSignIn(user)

        viewModelScope.launch {
            try {
                val userResponse = apiService.signIn(postDataSignIn)

                val statusCode = userResponse.code()

                val cookies = userResponse.headers().values("Set-Cookie")


                for (cookie in cookies) {
                    cookieManager.setCookie("https://app.restream.su", cookie)
                }


                val editor = sharedPreferences.edit()
                editor.putString("cookies", cookieManager.getCookie("https://app.restream.su"))
                editor.apply()






                _response.value = statusCode
            }
            catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса авторизации!!!! ${e.printStackTrace()}")
                _response.value = 0

            }
        }
    }


    fun checkUser(){

        val userList = mutableListOf<String>()

        viewModelScope.launch {
            try {
                val cookie = sharedPreferences.getString("cookies", "")
                val userResponse = apiService.user(cookie!!)



                val userEmail=userResponse.user_email
               val regdate = userResponse.registration_date
                val userTariff = userResponse.tariff!!.name

                userList.add(userEmail.toString())
                userList.add(regdate.toString())
                userList.add(userTariff)

                Log.d(TAG, "userEmail=${userEmail}")

             //   _responseUser.value = userEmail!!
                userListLiveData.value= userList


            }
            catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса проверки USER!!!! ${e.printStackTrace()}")
               // _responseUser.value = ""
                userListLiveData.value= listOf("")

            }


        }


    }



}