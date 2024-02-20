package com.example.restream

import android.content.Intent
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.restream.databinding.ActivityRegistrationBinding
const val TAG = "--Help--"
const val TAG_USER_EMAIL = "useremail"

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private  var isValidPass = false
    private  var isValidConfirmPass = false
    private  var isValidEmail = false
    private  var checkBoxFlag = false
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

            if (checkBoxFlag) {
                if (checkAllFormRegistartion()) {
                    val intent =
                        Intent(this@RegistrationActivity, MailConfirmationActivity::class.java)

                    intent.putExtra(TAG_USER_EMAIL, binding.email.text.toString())
                    startActivity(intent)

                }

            }
        }


        binding.loginInAcc.setOnClickListener {
            val spannableString = SpannableString(binding.loginInAcc.text)

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    binding.loginInAcc.setTypeface(null, Typeface.BOLD)

                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isFakeBoldText = true
                }
            }

            spannableString.setSpan(
                clickableSpan,
                0,
                binding.loginInAcc.text.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.loginInAcc.text = spannableString
            binding.loginInAcc.movementMethod = LinkMovementMethod.getInstance()

            val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
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
                Log.d(TAG, "Pass param= $isValidPass")
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
                Log.d(TAG, "Email param= $isValidEmail")
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
                Log.d(TAG, "Confirm pass param= $isValidConfirmPass")
            }
        })



    }




    }
