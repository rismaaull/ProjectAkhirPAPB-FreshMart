package com.papb.projectakhirandroid.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.papb.projectakhirandroid.data.local.ProductDatabase
import com.papb.projectakhirandroid.utils.Constants.PRODUCT_DATABASE
import com.papb.projectakhirandroid.utils.DataDummy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        // Hilt will inject the callback we're about to define
        callback: RoomDatabase.Callback
    ): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            PRODUCT_DATABASE
        )
        .fallbackToDestructiveMigration()
        .addCallback(callback) // Add the callback to the builder
        .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseCallback(
        // Use Provider to lazily get the database, breaking the circular dependency
        dbProvider: Provider<ProductDatabase>
    ): RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {
            private val applicationScope = CoroutineScope(SupervisorJob())

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Launch a coroutine to pre-populate the database
                applicationScope.launch(Dispatchers.IO) {
                    dbProvider.get().productDao().insertProducts(DataDummy.generateDummyProduct())
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideProductDao(database: ProductDatabase) = database.productDao()
}