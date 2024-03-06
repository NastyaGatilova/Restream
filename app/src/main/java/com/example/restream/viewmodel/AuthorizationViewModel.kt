package com.example.restream.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restream.TAG
import com.example.restream.databinding.ActivityAuthorizationBinding
import com.example.restream.retrofit.PostDataSignIn
import com.example.restream.retrofit.RetrofitClient
import com.example.restream.retrofit.User
import kotlinx.coroutines.launch


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
                val userResponse = RetrofitClient.apiService.signIn(postDataSignIn)

                val statusCode = userResponse.code()


                _response.value = statusCode
            } catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса авторизации!!!! ${e.printStackTrace()}")
                _response.value = 0

            }
        }
    }


    fun checkUser(binding: ActivityAuthorizationBinding) {

        val userList = mutableListOf<String>()

        viewModelScope.launch {

            try {
                val userResponse = RetrofitClient.apiService.user()

                val userEmail = userResponse.user_email
                val regdate = userResponse.registration_date
                val userTariff = userResponse.tariff!!.name

                userList.add(userEmail.toString())
                userList.add(regdate.toString())
                userList.add(userTariff)

                Log.d(TAG, "userEmail=${userEmail}")

                userListLiveData.value = userList


            } catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса проверки USER!!!! ${e.printStackTrace()}")
                userListLiveData.value = listOf("")

            }



        }


    }

    fun checkFormAuth(binding: ActivityAuthorizationBinding): Boolean {
        if ((binding.email.text!!.isNotEmpty()) && (binding.pass.text!!.isNotEmpty())) {
            binding.erPass.visibility = View.GONE
            binding.erEmail.visibility = View.GONE
            return true
        }
        if ((binding.email.text!!.isEmpty()) && (binding.pass.text!!.isEmpty())) {
            binding.erPass.visibility = View.VISIBLE
            binding.erEmail.visibility = View.VISIBLE
        } else if (binding.pass.text!!.isEmpty()) binding.erPass.visibility = View.VISIBLE
        else if (binding.email.text!!.isEmpty()) binding.erEmail.visibility = View.VISIBLE
        return false


    }


}