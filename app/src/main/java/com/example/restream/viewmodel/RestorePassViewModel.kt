package com.example.restream.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restream.TAG
import com.example.restream.databinding.ActivityRestorePassBinding
import com.example.restream.retrofit.PostDataSignIn
import com.example.restream.retrofit.RetrofitClient
import com.example.restream.retrofit.User
import kotlinx.coroutines.launch

class RestorePassViewModel(application: Application) : AndroidViewModel(application) {

    private val _response = MutableLiveData<Int>()
    val response: LiveData<Int>
        get() = _response


    fun passwordRequest(email:String) {
        val pass = User(email)
        val postDataPass = PostDataSignIn(pass)

        viewModelScope.launch {
            try {
                val userResponse = RetrofitClient.apiService.postPassword(postDataPass)

                val statusCode = userResponse.code()

                _response.value = statusCode


            } catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса к воостановлению пароля!!!! ${e.printStackTrace()}")
                _response.value = 0

            }


        }
    }


}