package com.example.restream.retrofit

import com.squareup.moshi.Json

data class Tariff (
    @Json(name = "name") val name: String,
    @Json(name = "start_at") val start_at: String,
    @Json(name = "max_online_outputs") val max_online_outputs: Int,
    @Json(name = "days_left") val days_left: String,

    )