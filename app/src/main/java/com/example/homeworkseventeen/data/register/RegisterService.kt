package com.example.homeworkseventeen.data.register


import com.example.homeworkseventeen.Request

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")

    suspend fun register(@Body request: Request): Response<RegisterResponseDto>

}