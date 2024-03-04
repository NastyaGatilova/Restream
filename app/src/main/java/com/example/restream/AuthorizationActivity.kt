package com.example.restream

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restream.databinding.ActivityAuthorizationBinding
import com.example.restream.viewmodel.AuthorizationViewModel



class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding
    val viewModel by lazy { ViewModelProvider(this).get(AuthorizationViewModel::class.java)}




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.registrBtn.setOnClickListener {
            binding.registrBtn.paintFlags = binding.registrBtn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            val intent = Intent(this@AuthorizationActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.restoreBtn.setOnClickListener {
            val intent = Intent(this@AuthorizationActivity, RestorePassActivity::class.java)
            startActivity(intent)
            }

        binding.comeInBtn.setOnClickListener {

            if (checkFormAuth()){
        //проверка успешности запроса

                viewModel.authUserRequest(binding)

                viewModel.response.observe(this, Observer { response ->

                if(response == 200) {

                    viewModel.checkUser()
                    viewModel.userListLiveData.observe(this, Observer { responseUser ->

                            //временная проверка вывода данных
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra(TAG_USER_EMAIL, responseUser.get(0))
                            intent.putExtra(TAG_USER_DATE, responseUser.get(1))
                            intent.putExtra(TAG_USER_TARIFF, responseUser.get(2))
                            startActivity(intent)
        //                    finish()



                    })


                }
                else if (response == 401){
                    Toast.makeText(this, R.string.wrong_email_or_pass, Toast.LENGTH_SHORT).show()
                }
                else {
                    val intent = Intent(this, ErrorActivity::class.java)
                    startActivity(intent)

            }


        })


            }
                else {
                binding.email.addTextChangedListener {
                    if (binding.email.text.isNotEmpty()) {
                        binding.erEmail.visibility=View.GONE
                    }
                    }
                    binding.pass.addTextChangedListener{
                        if(binding.pass.text!!.isNotEmpty()) {
                            binding.erPass.visibility=View.GONE
                        }
                    } }
        }


        }




    private fun checkFormAuth():Boolean{
        if ((binding.email.text!!.isNotEmpty()) && (binding.pass.text!!.isNotEmpty())){
            binding.erPass.visibility = View.GONE
            binding.erEmail.visibility = View.GONE
            return true
        }
        if ((binding.email.text!!.isEmpty()) && (binding.pass.text!!.isEmpty())){
            binding.erPass.visibility = View.VISIBLE
            binding.erEmail.visibility = View.VISIBLE
        }
        else if (binding.pass.text!!.isEmpty())    binding.erPass.visibility = View.VISIBLE
        else if (binding.email.text!!.isEmpty())    binding.erEmail.visibility = View.VISIBLE
        return false


    }

    override fun onResume() {
        super.onResume()
        val originalText = binding.registrBtn.text.toString()
        binding.registrBtn.setTextColor(Color.parseColor("#B223CA"))
        binding.registrBtn.text = originalText
        binding.registrBtn.paintFlags =  binding.registrBtn.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()


    }




}

