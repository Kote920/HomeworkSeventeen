package com.example.homeworkseventeen.domain.log_in

import com.example.homeworkseventeen.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface LogInRepository {

    suspend fun   logIn(email: String, password: String): Flow<Resource<LogInResponse>>
}