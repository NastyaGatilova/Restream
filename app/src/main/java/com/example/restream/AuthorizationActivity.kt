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
import com.example.restream.retrofit.ApiService
import com.example.restream.viewmodel.AuthorizationViewModel
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookiePolicy

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding
    val viewModel by lazy { ViewModelProvider(this).get(AuthorizationViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        VK.initialize(applicationContext)


        binding.registrBtn.setOnClickListener {
            binding.registrBtn.paintFlags =
                binding.registrBtn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            val intent = Intent(this@AuthorizationActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.restoreBtn.setOnClickListener {
            val intent = Intent(this@AuthorizationActivity, RestorePassActivity::class.java)
            startActivity(intent)
        }

        binding.comeInBtn.setOnClickListener {

            if (viewModel.checkFormAuth(binding.email.text.toString(),
                    binding.pass.text.toString())) {
                binding.erPass.visibility = View.GONE
                binding.erEmail.visibility = View.GONE
                //проверка успешности запроса
                binding.comeInBtn.setEnabled(false)
                viewModel.authUserRequest(binding.email.text.toString(), binding.pass.text.toString())
                viewModel.response.observe(this, Observer
                { response ->
                    binding.comeInBtn.setEnabled(false)
                    if (response == 200) {
                        viewModel.checkUser()
                        viewModel.userListLiveData.observe(this, Observer { responseUser ->
                            //временная проверка вывода данных


                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra(TAG_USER_EMAIL, responseUser.get(0))
                            intent.putExtra(TAG_USER_DATE, responseUser.get(1))
                            intent.putExtra(TAG_USER_TARIFF, responseUser.get(2))
                            startActivity(intent)
                            binding.email.text.clear()
                            binding.pass.text!!.clear()
                            finish()
                        })
                    } else if (response == 401) {
                        binding.erRequest.visibility = View.VISIBLE
                    } else {
                        val intent = Intent(this, ErrorActivity::class.java)
                        startActivity(intent)
                    }
                })

            } else {
                if ((binding.email.text!!.isEmpty()) && (binding.pass.text!!.isEmpty()))
                {
                   binding.erPass.visibility = View.VISIBLE
                   binding.erEmail.visibility = View.VISIBLE

                }else if (binding.pass.text!!.isEmpty())

                    binding.erPass.visibility = View.VISIBLE

                else if (binding.email.text!!.isEmpty())

                    binding.erEmail.visibility = View.VISIBLE

                binding.email.addTextChangedListener {
                    if (binding.email.text.isNotEmpty()) {
                        binding.erEmail.visibility = View.GONE
                    }
                }
                binding.pass.addTextChangedListener {
                    if (binding.pass.text!!.isNotEmpty()) {
                        binding.erPass.visibility = View.GONE
                    }
                }
            }


        }

       // Для получения SHA
     //   val fingerprints: Array<String?>? = VKUtils.getCertificateFingerprint(this, this.packageName)
        // Log.d("--fingerprints--", "${fingerprints!![0]}")

        //регистрация через соц сети
        val authLauncher = VK.login(this) { result : VKAuthenticationResult ->
//            when (result) {
//                is VKAuthenticationResult.Success -> {
//                    Log.d(TAG,"Успех")
//                    Log.d(TAG, "AccessToken =${result.token.accessToken}")
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
//
//
//
//                }
//                is VKAuthenticationResult.Failed -> {
//                    Log.d(TAG,"Неудача")
//                    val intent = Intent(this, ErrorActivity::class.java)
//                    startActivity(intent)
//                }
//            }
           if(viewModel.authVk(result)){
               val intent = Intent(this, HomeActivity::class.java)
               startActivity(intent)
           }
            else {
               val intent = Intent(this, ErrorActivity::class.java)
               startActivity(intent)
            }
        }
        binding.vk.setOnClickListener {
            authLauncher.launch(arrayListOf(VKScope.OFFLINE))
        }


    }





    override fun onResume() {
        super.onResume()
        val originalText = binding.registrBtn.text.toString()
        binding.registrBtn.text = originalText
        binding.registrBtn.paintFlags = binding.registrBtn.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        binding.erRequest.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        binding.comeInBtn.setEnabled(true)
    }


}

