package com.papb.projectakhirandroid.domain.usecase.searchproductusecase

import com.papb.projectakhirandroid.data.repository.Repository

class SearchProductUseCase(
    private val repository: Repository
) {

    operator fun invoke(query: String) = repository.searchProduct(query)

}