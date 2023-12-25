package com.example.homeworkseventeen.data.register

import com.example.homeworkseventeen.domain.register.RegisterResponse

fun RegisterResponseDto.toDomain(): RegisterResponse {
    return RegisterResponse(id, token)
}