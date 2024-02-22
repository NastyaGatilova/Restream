package com.example.restream

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.restream.databinding.ActivityAuthorizationBinding
import com.example.restream.databinding.ActivityMailConfirmationBinding



class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    private var isPasswordVisible = false




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
//            val intent = Intent(this@AuthorizationActivity, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
        }






    }

    override fun onResume() {
        super.onResume()
        val originalText = binding.registrBtn.text.toString()
        binding.registrBtn.setTextColor(Color.parseColor("#B223CA"))
        binding.registrBtn.text = originalText


    }




}

