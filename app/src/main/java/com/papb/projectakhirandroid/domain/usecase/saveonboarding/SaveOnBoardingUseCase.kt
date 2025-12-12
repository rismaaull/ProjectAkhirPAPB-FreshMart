package com.papb.projectakhirandroid.domain.usecase.saveonboarding

import com.papb.projectakhirandroid.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(isCompleted: Boolean) {
        repository.saveOnBoardingState(isCompleted = isCompleted)
    }

}