package com.papb.projectakhirandroid.domain.usecase.savelogin

import com.papb.projectakhirandroid.data.repository.Repository

class SaveLoginUseCase(private val repository: Repository) {
    suspend operator fun invoke(isLoggedIn: Boolean) {
        repository.saveLoginState(isLoggedIn)
    }
}
