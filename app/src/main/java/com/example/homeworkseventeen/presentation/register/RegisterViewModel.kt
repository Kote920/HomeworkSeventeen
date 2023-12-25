package com.example.homeworkseventeen.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkseventeen.Request
import com.example.homeworkseventeen.data.resource.Resource
import com.example.homeworkseventeen.data.register.RegisterResponseDto
import com.example.homeworkseventeen.domain.register.RegisterRepository
import com.example.homeworkseventeen.domain.register.RegisterResponse

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val repository: RegisterRepository): ViewModel() {

    private val _registerFlow = MutableStateFlow<Resource<RegisterResponse>>(
        Resource.Success(
            RegisterResponse(null, null)
        )
    )
    val registerFlow: StateFlow<Resource<RegisterResponse>> = _registerFlow.asStateFlow()


    fun register(email: String, password: String) {
        viewModelScope.launch {
            repository.register(email, password).collect{
            when(it){
                is Resource.Loading -> _registerFlow.value = Resource.Loading()
                is Resource.Success -> _registerFlow.value = Resource.Success(it.responseData)
                is Resource.Failed -> _registerFlow.value = Resource.Failed(it.message)

            }
        }
        }
    }
}

