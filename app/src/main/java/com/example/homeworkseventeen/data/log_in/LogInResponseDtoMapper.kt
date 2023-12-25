package com.example.homeworkseventeen.data.log_in

import com.example.homeworkseventeen.domain.log_in.LogInResponse

fun  LogInResponseDto.toDomain(): LogInResponse {
    return LogInResponse(token, email)
}