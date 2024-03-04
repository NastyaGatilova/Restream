package com.example.restream.retrofit

data class PostDataSignUp(
    val user: User,
    val ref_token: String?,
    val utm_tag: String?
)