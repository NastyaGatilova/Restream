package com.example.restream.retrofit

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api_auth/sign_up")
   suspend fun registrUser(@Body postData: PostData): Response<ResponseBody>


}