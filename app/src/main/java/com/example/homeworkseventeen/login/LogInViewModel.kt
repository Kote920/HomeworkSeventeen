package com.example.homeworkseventeen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkseventeen.Request

import com.example.homeworkseventeen.dataStore.DataStoreUtil
import com.example.homeworkseventeen.resource.Resource
import com.example.homeworkseventeen.responses.ResponseLogIn
import com.example.homeworksixteen.network.Network
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel() : ViewModel() {





    private val _logInFlow = MutableStateFlow<Resource<ResponseLogIn>>(
        Resource.Success(
            ResponseLogIn(null)
        )
    )
    val logInFlow: StateFlow<Resource<ResponseLogIn>> = _logInFlow.asStateFlow()

    private val _successFlow = MutableSharedFlow<LogInFragmentNavigationEvent>()
    val successFlow: SharedFlow<LogInFragmentNavigationEvent> get() = _successFlow


    fun logIn(email: String, password: String) {
        val request = Request(email, password)
        viewModelScope.launch {
            try {
                _logInFlow.value = Resource.Loading()

                val response = Network.loginService().logIn(request)

                if (response.isSuccessful) {
                    val activeUser = response.body()!!
                    activeUser.email = email


                    _logInFlow.value = Resource.Success(activeUser)
                } else {
                    _logInFlow.value = Resource.Failed("Request failed")
                }
            } catch (e: Exception) {
                _logInFlow.value = Resource.Failed(e.message?: "Unknown error")

            }
        }
    }



    fun saveToken(token: String, email: String, remember : Boolean){
        viewModelScope.launch {
//            Log.d("savedBooleanData", remember.toString())
            DataStoreUtil.saveData(token, email, remember)
            _successFlow.emit(LogInFragmentNavigationEvent.NavigationToHome)
        }

    }


}

sealed class LogInFragmentNavigationEvent{
    object NavigationToHome: LogInFragmentNavigationEvent()
}