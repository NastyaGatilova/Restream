package com.example.restream

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.restream.retrofit.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel(application: Application): AndroidViewModel(application) {


    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val converterFactory = MoshiConverterFactory.create(moshi)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://app.restream.su/")
        .addConverterFactory(converterFactory)
        .build()
    val service = retrofit.create(ApiService::class.java)

//    init {
//        viewModelScope.launch {
//             async {  requestRegistrUser(service) }
//        }
//
//    }
//
//     suspend fun requestRegistrUser(service: ApiService) {
//        try{
//
//            service.registrUser(
//                SignInRequest(
//                    binding.
//                )
//            )
//
//
//            //запрос
//
//
//        }
//        catch (e: Exception) {
//
//            Log.d("--Help--", "ERROR!!!!")
//
//
//        }
//    }


}