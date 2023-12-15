package com.example.homeworkseventeen.responses

import com.squareup.moshi.Json

data class ResponseLogIn(@Json(name = "token") var token: String? = null, var email: String? = null)