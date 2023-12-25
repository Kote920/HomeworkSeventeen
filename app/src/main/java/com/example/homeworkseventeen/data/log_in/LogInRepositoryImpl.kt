package com.example.homeworkseventeen.data.log_in

import com.example.homeworkseventeen.Request
import com.example.homeworkseventeen.data.register.RegisterService
import com.example.homeworkseventeen.domain.log_in.LogInRepository
import com.example.homeworkseventeen.domain.log_in.LogInResponse
import com.example.homeworkseventeen.data.resource.Resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(val logInService: LogInService): LogInRepository {
    override suspend fun logIn(email: String, password: String): Flow<Resource<LogInResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = logInService.logIn(Request(email, password))

                if (response.isSuccessful) {
                    val activeUser = response.body()!!
                    activeUser.email = email

                    emit(Resource.Success(activeUser.toDomain()))
                } else {
                    emit(Resource.Failed("Request failed"))
                }
            } catch (e: Exception) {
                emit(Resource.Failed("Request failed"))

            }
        }

    }
}