package com.papb.projectakhirandroid.domain.usecase.getselectedproduct

import com.papb.projectakhirandroid.data.repository.Repository
import com.papb.projectakhirandroid.domain.model.ProductItem

class GetSelectedProductUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(productId: Int): ProductItem {
        return repository.getSelectedProduct(productId = productId)
    }

}