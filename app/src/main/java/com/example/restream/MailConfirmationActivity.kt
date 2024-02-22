package com.example.restream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restream.databinding.ActivityMailConfirmationBinding

class MailConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMailConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMailConfirmationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.userEmail.text = intent.getStringExtra(TAG_USER_EMAIL).toString()

        binding.comeInBtn.setOnClickListener {
            val intent =
                Intent(this@MailConfirmationActivity, AuthorizationActivity::class.java)
            startActivity(intent)
        }

    }
}