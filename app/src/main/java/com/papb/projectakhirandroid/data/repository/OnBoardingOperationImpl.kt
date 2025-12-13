package com.papb.projectakhirandroid.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.papb.projectakhirandroid.data.dataStore
import com.papb.projectakhirandroid.domain.repository.OnBoardingOperation
import com.papb.projectakhirandroid.utils.Constants.ON_BOARDING_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnBoardingOperationImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : OnBoardingOperation {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = ON_BOARDING_KEY)
    }

    override suspend fun saveOnBoardingState(isCompleted: Boolean) {
        context.dataStore.edit {
            it[PreferencesKey.onBoardingKey] = isCompleted
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[PreferencesKey.onBoardingKey] ?: false
        }
    }

}
