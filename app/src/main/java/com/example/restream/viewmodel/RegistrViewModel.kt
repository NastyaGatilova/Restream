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
import com.example.restream.retrofit.PostDataSignUp
import com.example.restream.retrofit.User
import kotlinx.coroutines.launch


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