package com.example.homeworkseventeen.domain.register

import com.example.homeworkseventeen.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun register(email: String, password: String): Flow<Resource<RegisterResponse>>
}