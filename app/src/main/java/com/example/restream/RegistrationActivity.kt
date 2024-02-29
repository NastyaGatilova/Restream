package com.example.restream

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restream.databinding.ActivityRegistrationBinding
import com.example.restream.retrofit.ApiService
import com.example.restream.retrofit.PostData
import com.example.restream.retrofit.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val TAG = "--Help--"
const val TAG_USER_EMAIL = "useremail"

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private  var isValidPass = false
    private  var isValidConfirmPass = false
    private  var isValidEmail = false
    private  var checkBoxFlag = false

    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java)}





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        checkFormRegistration()


        binding.checkBoxOffer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxFlag = true
            } else {
                checkBoxFlag = false

            }
        }

        binding.registerbtn.setOnClickListener{
//            Log.d(TAG, "checkBoxFlag = $checkBoxFlag")
            if ( (checkBoxFlag == false) )
                //&& (binding.pass.text!!.isEmpty())&& (binding.confirmPass.text!!.isEmpty()) && (binding.email.text!!.isEmpty()))
                {
                binding.erPass.visibility = View.VISIBLE
                binding.erConfPass.visibility = View.VISIBLE
                binding.erEmail.visibility = View.VISIBLE
            }

            if (checkBoxFlag) {
                if (checkAllFormRegistartion()) {
                    //надо передать заполненные поля email,pass,confirmPass в MainViewModel
                        viewModel.registrUserRequest(binding)



                        viewModel.response.observe(this, Observer { response ->
                                if(response == 422)  Toast.makeText(this@RegistrationActivity, R.string.user_exists, Toast.LENGTH_SHORT).show()

                                else if (response == 201){

                                    val intent = Intent(this@RegistrationActivity, MailConfirmationActivity::class.java)
                                    intent.putExtra(TAG_USER_EMAIL, binding.email.text.toString())
                                    startActivity(intent)

                                }
                                else {
                                    val intent =
                                        Intent(this@RegistrationActivity, ErrorActivity::class.java)
                                    startActivity(intent)
                                }


                        })


                    }


                }

                }





        binding.loginInAcc.setOnClickListener {
            binding.loginInAcc.paintFlags = binding.loginInAcc.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            val intent = Intent(this@RegistrationActivity, AuthorizationActivity::class.java)
            startActivity(intent)
//            finish()
        }

        }

    override fun onResume() {
        super.onResume()
        binding.registerbtn.setText(R.string.register2)
    }


private fun checkAllFormRegistartion():Boolean{
    if ((isValidPass ) && (isValidConfirmPass )  &&(isValidEmail) && (binding.pass.text!!.isNotEmpty()) && (binding.email.text!!.isNotEmpty()) && (binding.confirmPass.text!!.isNotEmpty()) ) {
        return true
    }
    if ((binding.pass.text!!.isEmpty())&& (binding.confirmPass.text!!.isEmpty()) && (binding.email.text!!.isEmpty())){
        binding.erPass.visibility = View.VISIBLE
        binding.erConfPass.visibility = View.VISIBLE
        binding.erEmail.visibility = View.VISIBLE
    }
    else if (binding.pass.text!!.isEmpty())    binding.erPass.visibility = View.VISIBLE
    else if (binding.confirmPass.text!!.isEmpty())    binding.erConfPass.visibility = View.VISIBLE
    else if (binding.email.text!!.isEmpty())    binding.erEmail.visibility = View.VISIBLE
    return false
}

    private fun checkFormRegistration(){

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
                if ((s.length < 8 ) || (s.length >128 )) {
                    binding.erPass.visibility = View.VISIBLE
                    isValidPass = false
                }
                else {
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
                    // Введенный текст является недопустимым email-адресом
                    binding.erEmail.visibility = View.VISIBLE
                    isValidEmail = false
                }
                else {
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
                if (binding.confirmPass.text.toString() != binding.pass.text.toString()){
                    binding.erConfPass.visibility = View.VISIBLE
                    isValidConfirmPass = false
                }

                else {
                    binding.erConfPass.visibility = View.GONE
                    isValidConfirmPass = true
                }
            }
        })



    }




    }
