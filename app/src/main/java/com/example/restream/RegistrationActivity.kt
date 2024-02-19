package com.example.restream

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.restream.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.registerbtn.setOnClickListener{

            val email: String = binding.email.getText().toString()
            val pass: String = binding.pass.getText().toString()
            val confirmPass: String = binding.confirmPass.getText().toString()

//            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                // Почта пустая или не соответствует формату email
//                // Выводим сообщение об ошибке
//                // emailEditText.error = "Введите корректный адрес электронной почты"
//                email.
//            }
//
//            if (pass.isEmpty() || pass.length < 8 || pass.length > 128) {
//                // Пароль пустой или не соответствует длине
//                // Выводим сообщение об ошибке
//                //passwordEditText.error = "Пароль должен содержать от 8 до 128 символов"
//            }
//
//
//
//            if (confirmPass != pass) {
//                // Подтверждение пароля не совпадает с паролем
//                // Выводим сообщение об ошибке
//              //  confirmPasswordEditText.error = "Пароли не совпадают"
//            }
        }

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
                if ((s.length < 8 ) || (s.length >128 )) binding.passTextInputLayout.setError(
                    "В пароле должно быть от 8 символов"
                ) else binding.passTextInputLayout.setError(null)
            }
        })

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
                    if ((s.length < 8 ) || (s.length >128 )) binding.passTextInputLayout.setError(
                        "В пароле должно быть от 8 символов"
                    ) else binding.passTextInputLayout.setError(null)
                }
            })










        }


       
    }
