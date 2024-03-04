package com.example.restream.retrofit

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "error") val error: String?=null
)
