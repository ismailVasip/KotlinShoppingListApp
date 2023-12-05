package com.example.kotlinshoppingapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity
data class ShoppingList(

    @ColumnInfo(name = "tittle")
    var tittle:String,

    @ColumnInfo(name = "description")
    var description:String,

    @ColumnInfo(name = "list")
    var list: MutableList<String> ) :Serializable {


    @PrimaryKey(autoGenerate = true)
    var id = 0
}

class TypeConverter{

    @TypeConverter
    fun fromString(value:String?):MutableList<String?> {
        val listType = object : TypeToken<MutableList<String>>(){}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromList(list :MutableList<String?>) :String{
        return Gson().toJson(list)
    }
}
