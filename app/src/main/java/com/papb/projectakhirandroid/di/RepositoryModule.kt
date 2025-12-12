package com.papb.projectakhirandroid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.papb.projectakhirandroid.data.repository.OnBoardingOperationImpl
import com.papb.projectakhirandroid.data.repository.Repository
import com.papb.projectakhirandroid.domain.repository.OnBoardingOperations
import com.papb.projectakhirandroid.domain.usecase.UseCases
import com.papb.projectakhirandroid.domain.usecase.addcartusecase.AddCartUseCase
import com.papb.projectakhirandroid.domain.usecase.deletecartusecase.DeleteCartUseCase
import com.papb.projectakhirandroid.domain.usecase.getallcartusecase.GetAllCartUseCase
import com.papb.projectakhirandroid.domain.usecase.getallproduct.GetAllProductUseCase
import com.papb.projectakhirandroid.domain.usecase.getselectedproduct.GetSelectedProductUseCase
import com.papb.projectakhirandroid.domain.usecase.readonboarding.ReadOnBoardingUseCase
import com.papb.projectakhirandroid.domain.usecase.saveonboarding.SaveOnBoardingUseCase
import com.papb.projectakhirandroid.domain.usecase.saveproductusecase.InsertProductsUseCase
import com.papb.projectakhirandroid.domain.usecase.searchproductusecase.SearchProductUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperation(
        @ApplicationContext context: Context
    ): OnBoardingOperations = OnBoardingOperationImpl(context = context)

    @Provides
    @Singleton
    fun provideUseCase(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            insertProductsUseCase = InsertProductsUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getSelectedProductUseCase = GetSelectedProductUseCase(repository),
            getAllProductUseCase = GetAllProductUseCase(repository),
            getAllCartUseCase = GetAllCartUseCase(repository),
            addCartUseCase = AddCartUseCase(repository),
            deleteCart = DeleteCartUseCase(repository),
            searchProductUseCase = SearchProductUseCase(repository)
        )
    }

}