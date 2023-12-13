package com.example.homeworksixteen.service

import com.example.homeworkseventeen.Request
import com.example.homeworkseventeen.resource.Resource
import com.example.homeworkseventeen.responses.ResponseLogIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInService {

    @POST("login")
    suspend fun logIn(@Body request: Request): Response<ResponseLogIn>

}