package com.example.homeworkseventeen.presentation.splash

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkseventeen.dataStore.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

class SplashViewModel : ViewModel() {

    private val _sessionFlow = MutableSharedFlow<Array<String>>()
    val sessionFlow: SharedFlow<Array<String>> get() = _sessionFlow

    fun readSession() {
        viewModelScope.launch {
            DataStoreUtil.readTokenAndRemember().collect {
                if (!it.isNullOrEmpty()){
                    d("readSession", it[0] + it[1] )


                    _sessionFlow.emit(it)
                }else{
                    _sessionFlow.emit(arrayOf("",""))
                }

            }
        }

    }
}