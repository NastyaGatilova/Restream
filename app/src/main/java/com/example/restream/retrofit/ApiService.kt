package com.example.restream.retrofit

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api_auth/sign_up")

   suspend fun signUp(@Body postDataSignUp: PostDataSignUp): Response<ResponseBody>

    @POST("api_auth/sign_in")
   suspend fun signIn(@Body postDataSignIn: PostDataSignIn): Response<ResponseBody>

    @GET("api/user")
    suspend fun user(): User

    @POST("api_auth/password")
    suspend fun postPassword(@Body postDataSignIn: PostDataSignIn): Response<ResponseBody>




}