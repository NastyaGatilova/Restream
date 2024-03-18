package com.example.restream

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.restream.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val email = intent.getStringExtra(TAG_USER_EMAIL).toString()
        Log.d(TAG, "email from HomeActivity= $email")
       // val date = intent.getStringExtra(TAG_USER_DATE).toString()
      //  val tariff = intent.getStringExtra(TAG_USER_TARIFF).toString()

        val bundle = Bundle()
        bundle.putString("emailFromHomeAct", email)

        val fragment = Home()
        fragment.arguments = bundle


        viewPager2 = binding.viewPager
        bottomNavigationView = binding.bottomNav

        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager2.adapter = viewPagerAdapter

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    viewPager2.currentItem = 0

                    return@setOnItemSelectedListener true
                }
                R.id.bottom_broadcasts -> {
                    viewPager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_mytariff -> {
                    viewPager2.currentItem = 2
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationView.menu.findItem(R.id.bottom_home).isChecked = true
                    1 -> bottomNavigationView.menu.findItem(R.id.bottom_broadcasts).isChecked = true
                    2 -> bottomNavigationView.menu.findItem(R.id.bottom_mytariff).isChecked = true
                }

            }
        })





        //binding.textView5.text = "Добро пожаловать, ${intent.getStringExtra(TAG_USER_EMAIL).toString()}\n\n Дата регистрации: ${intent.getStringExtra(TAG_USER_DATE).toString()}\n\n Ваш тариф: ${intent.getStringExtra(TAG_USER_TARIFF).toString()}"
    }
}



