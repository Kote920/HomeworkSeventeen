package com.example.homeworkseventeen.responses

import com.squareup.moshi.Json

data class ResponseRegister(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "token") var token: String? = null


)

