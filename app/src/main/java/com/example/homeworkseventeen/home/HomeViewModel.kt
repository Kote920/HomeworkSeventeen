package com.example.homeworkseventeen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkseventeen.dataStore.DataStoreUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _sessionFlow = MutableSharedFlow<Array<String>>()
    val sessionFlow: SharedFlow<Array<String>> get() = _sessionFlow

    fun killSession() {
        viewModelScope.launch {
                DataStoreUtil.clearSession()
        }

    }
}