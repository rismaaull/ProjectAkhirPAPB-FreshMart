package com.papb.projectakhirandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.papb.projectakhirandroid.data.local.dao.ProductDao
import com.papb.projectakhirandroid.domain.model.ProductItem

@Database(entities = [ProductItem::class], version = 2)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}