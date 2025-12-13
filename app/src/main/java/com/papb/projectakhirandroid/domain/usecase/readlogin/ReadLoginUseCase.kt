package com.papb.projectakhirandroid.domain.usecase.readlogin

import com.papb.projectakhirandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadLoginUseCase(private val repository: Repository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readLoginState()
    }
}
