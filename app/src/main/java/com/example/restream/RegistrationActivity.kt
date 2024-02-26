package com.example.restream

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.restream.databinding.ActivityRegistrationBinding
import com.example.restream.retrofit.ApiService
import com.example.restream.retrofit.SignInRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TAG = "--Help--"
const val TAG_USER_EMAIL = "useremail"

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private  var isValidPass = false
    private  var isValidConfirmPass = false
    private  var isValidEmail = false
    private  var checkBoxFlag = false

    private lateinit var apiService: ApiService

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
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            apiService.registrUser(
                                SignInRequest(
                                    binding.email.text.toString(),
                                    binding.pass.text.toString(),
                                    binding.confirmPass.text.toString()
                                )
                            )
                            Log.d(TAG, "Успешно")
                        }
                        catch (e: Exception) {

                            Log.d(TAG, "Ошибка запроса!!!!")


                        }
                    }


//                    val intent =
//                        Intent(this@RegistrationActivity, MailConfirmationActivity::class.java)
//
//                    intent.putExtra(TAG_USER_EMAIL, binding.email.text.toString())
//                    startActivity(intent)

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
//                Log.d(TAG, "Pass param= $isValidPass")
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
//                Log.d(TAG, "Email param= $isValidEmail")
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
//                Log.d(TAG, "Confirm pass param= $isValidConfirmPass")
            }
        })



    }




    }
