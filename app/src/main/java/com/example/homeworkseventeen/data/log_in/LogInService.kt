package com.example.homeworkseventeen.data.log_in

import com.example.homeworkseventeen.Request
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInService {

    @POST("login")
    suspend fun logIn(@Body request: Request): Response<LogInResponseDto>

}