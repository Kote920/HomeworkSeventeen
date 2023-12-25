package com.example.homeworkseventeen.data.register

import com.example.homeworkseventeen.Request
import com.example.homeworkseventeen.domain.register.RegisterRepository
import com.example.homeworkseventeen.domain.register.RegisterResponse
import com.example.homeworkseventeen.data.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(val registerService: RegisterService) : RegisterRepository {

    override suspend fun register(
        email: String,
        password: String
    ): Flow<Resource<RegisterResponse>> {
        return flow {
            try {

                emit(Resource.Loading())

                val response = registerService.register(Request(email, password))

                if (response.isSuccessful) {
                    val registeredUser = response.body()!!
                    emit(Resource.Success(registeredUser.toDomain()))
                } else {
                    emit(Resource.Failed("Request failed"))
                }
            } catch (e: Exception) {
                emit(Resource.Failed(e.message ?: "Unknown error"))
            }
        }

    }
}