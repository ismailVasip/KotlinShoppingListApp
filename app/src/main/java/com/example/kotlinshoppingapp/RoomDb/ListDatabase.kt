package com.example.kotlinshoppingapp.RoomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlinshoppingapp.Model.ShoppingList
import com.example.kotlinshoppingapp.Model.TypeConverter

@Database(entities = [ShoppingList::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class ListDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
}