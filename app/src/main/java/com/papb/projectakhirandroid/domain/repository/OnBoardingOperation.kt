package com.papb.projectakhirandroid.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnBoardingOperation {
    suspend fun saveOnBoardingState(isCompleted: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
    suspend fun saveLoginState(isLoggedIn: Boolean)
    fun readLoginState(): Flow<Boolean>
}
