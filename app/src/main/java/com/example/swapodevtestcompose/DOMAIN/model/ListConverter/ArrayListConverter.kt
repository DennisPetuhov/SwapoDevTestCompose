package com.example.swapidevtest.DOMAIN.model.ListConverter

import androidx.room.TypeConverter
import com.example.swapidevtest.DOMAIN.TypeConverter.fromJson
import com.google.gson.Gson

class ArrayListConverter {

    @TypeConverter
    fun fromStringArrayList(value: ArrayList<String>): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}