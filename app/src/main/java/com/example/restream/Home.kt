package com.example.restream

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restream.databinding.FragmentHomeBinding


class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = arguments?.getString("emailFromHomeAct")
       //  val date = arguments?.getString(TAG_USER_DATE)
       //   val tariff = arguments?.getString(TAG_USER_TARIFF)
        Log.d(TAG, "email from fragment home = ${email}")
       //   binding.textView5.text = "Добро пожаловать, ${email}\n\n "
       //   binding.textView5.text = "Добро пожаловать, ${intent.getStringExtra(TAG_USER_EMAIL).toString()}\n\n Дата регистрации: ${intent.getStringExtra(TAG_USER_DATE).toString()}\n\n Ваш тариф: ${intent.getStringExtra(TAG_USER_TARIFF).toString()}"


    }


}