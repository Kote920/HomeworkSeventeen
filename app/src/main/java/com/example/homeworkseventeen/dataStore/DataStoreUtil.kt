package com.example.homeworkseventeen.dataStore

import android.content.Context
import android.util.Log.d
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.homeworkseventeen.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.log


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")



object DataStoreUtil {
    val TOKEN = stringPreferencesKey("token")
    val EMAIL = stringPreferencesKey("email")
    val REMEMBER = booleanPreferencesKey("remember")

    suspend fun saveData(token: String, email: String, remember: Boolean) {

        App.application.applicationContext.dataStore.edit { settings ->
            d("kkp", "jj")
            settings[TOKEN] = token
            settings[EMAIL] = email
            settings[REMEMBER] = remember

        }



    }

    fun readTokenAndRemember(): Flow<Array<String>> = App.application.applicationContext.dataStore.data
        .map { preferences ->
            val email = preferences[EMAIL] ?: ""
            val remember = preferences[REMEMBER] ?: false
            arrayOf(email, remember.toString())
        }
}