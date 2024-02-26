package com.example.restream.retrofit

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api_auth/sign_up")
   suspend fun registrUser(@Body signInRequest:SignInRequest)


}