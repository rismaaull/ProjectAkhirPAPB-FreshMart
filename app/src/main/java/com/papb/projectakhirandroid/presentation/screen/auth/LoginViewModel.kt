package com.papb.projectakhirandroid.presentation.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papb.projectakhirandroid.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    fun saveLoginState(isLoggedIn: Boolean) {
        viewModelScope.launch {
            useCases.saveLoginUseCase(isLoggedIn)
        }
    }
}
