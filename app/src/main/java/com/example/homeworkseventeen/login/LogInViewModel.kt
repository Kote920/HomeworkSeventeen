package com.example.homeworkseventeen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkseventeen.Request

import com.example.homeworkseventeen.SessionManager
import com.example.homeworkseventeen.resource.Resource
import com.example.homeworkseventeen.responses.ResponseLogIn
import com.example.homeworksixteen.network.Network
import com.example.homeworksixteen.service.LogInService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel(private val sessionManager: SessionManager) : ViewModel() {





    private val _logInFlow = MutableStateFlow<Resource<ResponseLogIn>>(
        Resource.Success(
            ResponseLogIn(null)
        )
    )
    val logInFlow: StateFlow<Resource<ResponseLogIn>> = _logInFlow.asStateFlow()


    fun logIn(email: String, password: String) {
        val request = Request(email, password)
        viewModelScope.launch {
            try {
                _logInFlow.value = Resource.Loading()

                val response = Network.loginService().logIn(request)

                if (response.isSuccessful) {
                    val activeUser = response.body()!!
                    sessionManager.userEmail = email
                    sessionManager.userToken = activeUser.token
                    sessionManager.isLoggedIn = true

                    _logInFlow.value = Resource.Success(activeUser)
                } else {
                    _logInFlow.value = Resource.Failed("Request failed")
                }
            } catch (e: Exception) {
                _logInFlow.value = Resource.Failed(e.message?: "Unknown error")

            }
        }
    }
    fun checkSession(): Boolean {
        return sessionManager.isLoggedIn
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sessionManager.isLoggedIn = isLoggedIn
    }
    fun setRemember(remember: Boolean){
        sessionManager.remember = remember
    }
    fun checkRemember():Boolean{
        return sessionManager.remember
    }

    fun logOut(){
        sessionManager.clearSession()
    }
    fun getEmail(): String?{
        return sessionManager.userEmail
    }



}