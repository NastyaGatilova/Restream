package com.example.restream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.restream.databinding.ActivityRestorePassBinding
import com.example.restream.viewmodel.RestorePassViewModel

class RestorePassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestorePassBinding
    val viewModel by lazy { ViewModelProvider(this).get(RestorePassViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePassBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.restoreBtn.setOnClickListener {

            if (checkFormEmail()){
                viewModel.passwordRequest(binding)

                viewModel.response.observe(this, Observer { response ->

                    if(response == 200) {

                        binding.textView.setText(R.string.instructions_sent)
                        binding.restoreBtn.visibility = View.GONE
                        binding.email.visibility = View.GONE
                    }

                    else {

                        val intent =
                            Intent(this, ErrorActivity::class.java)
                        startActivity(intent)
                    }


                })
            }
            else {
                binding.email.addTextChangedListener {
                    if (binding.email.text.isNotEmpty()) {
                        binding.erEmail.visibility=View.GONE
                    }
                } }
            finish()
        }

        binding.backBtn.setOnClickListener{
            val intent =
                Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
          //  finish()
        }
    }







    private fun checkFormEmail():Boolean{
        if ((binding.email.text!!.isNotEmpty()) && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text!!).matches()){
            binding.erEmail.visibility = View.GONE
            return true
        }
        else  binding.erEmail.visibility = View.VISIBLE
        return false


    }
}