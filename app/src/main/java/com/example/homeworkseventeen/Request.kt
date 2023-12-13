package com.example.homeworkseventeen

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)