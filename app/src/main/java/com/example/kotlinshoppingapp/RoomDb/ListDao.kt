package com.example.kotlinshoppingapp.RoomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.kotlinshoppingapp.Model.ShoppingList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ListDao {

    @Query("SELECT * FROM ShoppingList")
    fun getAll() :Flowable<MutableList<ShoppingList>>

    @Insert
    fun insert(shoppingList: ShoppingList) :Completable

    @Delete
    fun delete(shoppingList: ShoppingList) :Completable

    @Update
    fun update(shoppingList: ShoppingList) :Completable

    @Query("SELECT COUNT(*) FROM ShoppingList")
    fun getRowCount(): Flowable<Int>
}