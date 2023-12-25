package com.example.homeworkseventeen.data.log_in

import com.squareup.moshi.Json

data class LogInResponseDto(@Json(name = "token") var token: String? = null, var email: String? = null)