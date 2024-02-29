package com.example.restream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restream.databinding.ActivityErrorBinding
import com.example.restream.databinding.ActivityMailConfirmationBinding

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.comeInBtn.setOnClickListener {
            val intent =
                Intent(this@ErrorActivity, AuthorizationActivity::class.java)
            startActivity(intent)
        }
    }
}