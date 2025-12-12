package com.papb.projectakhirandroid.domain.usecase.readonboarding

import com.papb.projectakhirandroid.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(private val repository: Repository) {

    operator fun invoke(): Flow<Boolean> = repository.readOnBoardingState()

}