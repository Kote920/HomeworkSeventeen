package com.example.homeworkseventeen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkseventeen.Request
import com.example.homeworkseventeen.resource.Resource
import com.example.homeworkseventeen.responses.ResponseLogIn
import com.example.homeworkseventeen.responses.ResponseRegister
import com.example.homeworksixteen.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val _registerFlow = MutableStateFlow<Resource<ResponseRegister>>(
        Resource.Success(
            ResponseRegister()
        )
    )
    val registerFlow: StateFlow<Resource<ResponseRegister>> = _registerFlow.asStateFlow()


    fun register(email: String, password: String) {
        val request = Request(email, password)
        viewModelScope.launch {
            try {


                _registerFlow.value = Resource.Loading()

                val response = Network.registerService().register(request)

                if (response.isSuccessful) {
                    val registeredUser = response.body()!!
                    _registerFlow.value =  Resource.Success(registeredUser)
                } else {
                   _registerFlow.value =  Resource.Failed("Request failed")
                }
            } catch (e: Exception) {
                _registerFlow.value =   Resource.Failed(e.message ?: "Unknown error")
            }
        }
    }
}

