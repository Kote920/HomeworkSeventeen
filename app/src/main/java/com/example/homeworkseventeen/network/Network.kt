package com.example.homeworksixteen.network

import com.example.homeworksixteen.service.LogInService
import com.example.homeworksixteen.service.RegisterService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {

    const val BASE_URL = "https://reqres.in/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }


    fun loginService(): LogInService = retrofit.create(LogInService::class.java)
    fun registerService(): RegisterService = retrofit.create(RegisterService::class.java)
}