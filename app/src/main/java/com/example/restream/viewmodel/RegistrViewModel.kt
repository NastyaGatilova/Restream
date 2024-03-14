package com.example.restream.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restream.R
import com.example.restream.TAG
import com.example.restream.databinding.ActivityRegistrationBinding
import com.example.restream.retrofit.PostDataSignUp
import com.example.restream.retrofit.RetrofitClient
import com.example.restream.retrofit.User
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
class RegistrViewModel(application: Application) : AndroidViewModel(application) {

    private var isValidPass = false
    private var isValidConfirmPass = false
    private var isValidEmail = false
    private val _response = MutableLiveData<Int>()
    val response: LiveData<Int>
        get() = _response


    fun registrUserRequest(email:String,pass:String,confirmPass:String) {
        val signUp = User(email,pass, confirmPass)
        val postDataSignUp = PostDataSignUp(signUp, null, null)

        viewModelScope.launch {
            try {
                val userResponse = RetrofitClient.apiService.signUp(postDataSignUp)

                val statusCode = userResponse.code()

                _response.value = statusCode


            } catch (e: Exception) {
                Log.d(TAG, "Ошибка запроса!!!! ${e.printStackTrace()}")
//                binding.registerbtn.setText(R.string.wait)
                _response.value = 0

            }
        }


    }

    fun checkAllFormRegistartion( email: String, pass: String,confirmPass: String): Boolean {
        if ((isValidPass) && (isValidConfirmPass) && (isValidEmail) && (pass.isNotEmpty()) && (email.isNotEmpty()) && (confirmPass.isNotEmpty())) {
            return true
        }
//        if ((binding.pass.text!!.isEmpty()) && (binding.confirmPass.text!!.isEmpty()) && (binding.email.text!!.isEmpty())) {
//            binding.erPass.visibility = View.VISIBLE
//            binding.erConfPass.visibility = View.VISIBLE
//            binding.erEmail.visibility = View.VISIBLE
//        } else if (binding.pass.text!!.isEmpty()) binding.erPass.visibility = View.VISIBLE
//        else if (binding.confirmPass.text!!.isEmpty()) binding.erConfPass.visibility = View.VISIBLE
//        else if (binding.email.text!!.isEmpty()) binding.erEmail.visibility = View.VISIBLE
        return false
    }

    fun checkFormRegistration(binding: ActivityRegistrationBinding) {

        binding.pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if ((s.length < 8) || (s.length > 128)) {
                    binding.erPass.visibility = View.VISIBLE
                    isValidPass = false
                } else {
                    isValidPass = true
                    binding.erPass.visibility = View.GONE
                }
            }

        })

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val email = s.toString()
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.erEmail.visibility = View.VISIBLE
                    isValidEmail = false
                } else {
                    isValidEmail = true
                    binding.erEmail.visibility = View.GONE
                }

            }
        })

        binding.confirmPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (binding.confirmPass.text.toString() != binding.pass.text.toString()) {
                    binding.erConfPass.visibility = View.VISIBLE
                    isValidConfirmPass = false
                } else {
                    binding.erConfPass.visibility = View.GONE
                    isValidConfirmPass = true
                }
            }
        })


    }


}