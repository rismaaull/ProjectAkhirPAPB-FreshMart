package com.papb.projectakhirandroid.domain.usecase.getallproduct

import com.papb.projectakhirandroid.data.repository.Repository
import com.papb.projectakhirandroid.domain.model.ProductItem
import kotlinx.coroutines.flow.Flow

class GetAllProductUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<List<ProductItem>> = repository.getAllProduct()

}