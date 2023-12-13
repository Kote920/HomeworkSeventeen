package com.example.homeworksixteen.service


import com.example.homeworkseventeen.Request
import com.example.homeworkseventeen.responses.ResponseLogIn
import com.example.homeworkseventeen.responses.ResponseRegister

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")

    suspend fun register(@Body request: Request): Response<ResponseRegister>

}