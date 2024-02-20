package com.example.restream

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.restream.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isPasswordVisible = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)







        binding.registrBtn.setOnClickListener {
                val spannableString = SpannableString(binding.registrBtn.text)

                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        binding.registrBtn.setTypeface(null, Typeface.BOLD)

                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isFakeBoldText = true
                    }
                }

                spannableString.setSpan(
                    clickableSpan,
                    0,
                    binding.registrBtn.text.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.registrBtn.text = spannableString
                binding.registrBtn.movementMethod = LinkMovementMethod.getInstance()

            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.restoreBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, RestorePassActivity::class.java)
            startActivity(intent)
            finish()
            }
        binding.comeInBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ComeInActivity::class.java)
            startActivity(intent)
            finish()
        }






    }

    override fun onResume() {
        super.onResume()
        val originalText = binding.registrBtn.text.toString()
        binding.registrBtn.setTextColor(Color.parseColor("#B223CA"))
        binding.registrBtn.text = originalText


    }




}

