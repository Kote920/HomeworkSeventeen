package com.example.homeworkseventeen.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkseventeen.Request

import com.example.homeworkseventeen.dataStore.DataStoreUtil
import com.example.homeworkseventeen.data.resource.Resource
import com.example.homeworkseventeen.data.log_in.LogInResponseDto
import com.example.homeworkseventeen.domain.log_in.LogInRepository
import com.example.homeworkseventeen.domain.log_in.LogInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(val repository: LogInRepository) : ViewModel() {





    private val _logInFlow = MutableStateFlow<Resource<LogInResponse>>(
        Resource.Success(
            LogInResponse(null, null)
        )
    )
    val logInFlow: StateFlow<Resource<LogInResponse>> = _logInFlow.asStateFlow()

    private val _successFlow = MutableSharedFlow<LogInFragmentNavigationEvent>()
    val successFlow: SharedFlow<LogInFragmentNavigationEvent> get() = _successFlow


    fun logIn(email: String, password: String) {

        viewModelScope.launch {
            repository.logIn(email, password).collect{
                when(it){
                    is Resource.Loading -> _logInFlow.value = Resource.Loading()
                    is Resource.Success -> _logInFlow.value = Resource.Success(it.responseData)
                    is Resource.Failed -> _logInFlow.value = Resource.Failed(it.message)

                }
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