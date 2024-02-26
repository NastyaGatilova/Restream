package com.example.restream.retrofit

data class SignInRequest (
    val email: String,
    val password: String,
    val password_confirmation: String

)