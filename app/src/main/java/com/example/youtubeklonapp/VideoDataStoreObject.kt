package com.example.youtubeklonapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VideoDataStoreObject(val dataStore: DataStore<Preferences>) {

    companion object{
        val FAVORITE = stringPreferencesKey("favorite")
    }



    val videoIdDataStoreFlow: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[FAVORITE]
        }

    suspend fun saveUserToPreferencesStore(videoId : String?) {
        dataStore.edit { preferences ->
            preferences[FAVORITE] = videoId!!
        }
    }
}