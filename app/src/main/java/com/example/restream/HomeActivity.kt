package com.example.restream

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restream.databinding.ActivityHomeBinding
import com.example.restream.databinding.ActivityMailConfirmationBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.textView5.text = "Добро пожаловать, ${intent.getStringExtra(TAG_USER_EMAIL).toString()}\n\n Дата регистрации: ${intent.getStringExtra(TAG_USER_DATE).toString()}\n\n Ваш тариф: ${intent.getStringExtra(
            TAG_USER_TARIFF).toString()}"
    }
}