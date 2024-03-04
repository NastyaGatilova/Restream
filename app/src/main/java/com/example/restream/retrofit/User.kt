package com.example.restream.retrofit

import com.squareup.moshi.Json

data class User(
    @Json(name = "email") val email: String? = null,
    @Json(name = "password") val password: String? = null,
    @Json(name = "password_confirmation") val password_confirmation: String? = null,


    @Json(name = "user_id") val user_id: Int? = null,
    @Json(name = "user_name") val user_name: String? = null,
    @Json(name = "user_email") val user_email: String? = null,
    @Json(name = "registration_date") val registration_date: String? = null,
    @Json(name = "chat_token") val chat_token: String? = null,
    @Json(name = "spend_ref_points") val ref_points: Int? = null,
    @Json(name = "referrals_count") val referrals_count: Int? = null,
    @Json(name = "tariff") val tariff: Tariff? = null,
    @Json(name = "chat_config") val chat_config: String? = null,
    @Json(name = "no_mailing") val no_mailing: Boolean? = null,

            )



