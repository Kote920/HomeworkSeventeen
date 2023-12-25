package com.example.homeworkseventeen.data.register

import com.squareup.moshi.Json

data class RegisterResponseDto(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "token") var token: String? = null


)

