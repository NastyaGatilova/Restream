package com.example.restream

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restream.databinding.ActivityRegistrationBinding
import com.example.restream.viewmodel.RegistrViewModel

const val TAG = "--Help--"
const val TAG_USER_EMAIL = "useremail"
const val TAG_USER_DATE = "userdate"
const val TAG_USER_TARIFF = "usertariff"
const val URL_PUBLIC_OFFER = "https://restream.su/offer"


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private var checkBoxFlag = false

    val viewModel by lazy { ViewModelProvider(this).get(RegistrViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.checkFormRegistration(binding)

        binding.checkBoxOffer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxFlag = true
            } else {
                checkBoxFlag = false
            }
        }

        binding.registerbtn.setOnClickListener {
            if ((checkBoxFlag == false)) {
                binding.erPass.visibility = View.VISIBLE
                binding.erConfPass.visibility = View.VISIBLE
                binding.erEmail.visibility = View.VISIBLE
            }

            if (checkBoxFlag) {
                if (viewModel.checkAllFormRegistartion(binding)) {
                    viewModel.registrUserRequest(binding)

                    viewModel.response.observe(this, Observer { response ->
                        if (response == 422) Toast.makeText(
                            this@RegistrationActivity,
                            R.string.user_exists,
                            Toast.LENGTH_SHORT
                        ).show()
                        else if (response == 201) {
                            val intent = Intent(
                                this@RegistrationActivity,
                                MailConfirmationActivity::class.java
                            )
                            intent.putExtra(TAG_USER_EMAIL, binding.email.text.toString())
                            startActivity(intent)

                        } else {
                            val intent =
                                Intent(this@RegistrationActivity, ErrorActivity::class.java)
                            startActivity(intent)
                        }

                    })

                }

            }
        }

        binding.publicOffer.setOnClickListener {
            binding.publicOffer.paintFlags =
                binding.publicOffer.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_PUBLIC_OFFER))
            startActivity(browserIntent)
        }


        binding.loginInAcc.setOnClickListener {
            binding.loginInAcc.paintFlags =
                binding.loginInAcc.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            val intent = Intent(this@RegistrationActivity, AuthorizationActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        binding.registerbtn.setText(R.string.register2)
        binding.publicOffer.paintFlags =
            binding.publicOffer.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        binding.loginInAcc.paintFlags =
            binding.loginInAcc.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()

    }




}
