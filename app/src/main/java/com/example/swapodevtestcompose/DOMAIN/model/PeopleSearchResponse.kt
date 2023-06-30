package com.example.swapidevtest.DOMAIN.model

import android.os.Parcelable
import androidx.room.Ignore
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class PeopleSearchResponse(
//    val count: Int = null,
//    val next: String? = null,
//    val previous: String? = null,
//    @Json(name = "results")
//    var people: List<Person>? = null
//) : Parcelable

@Parcelize
data class PeopleSearchResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    @Json(name = "results")
    var people: List<Person>? = null  // mutableListOf()
) : Parcelable

@Parcelize
data class Person(
    val name: String,
    val starships: List<String>,
    val gender: String,
    val films: List<String>,
    val homeworld: String,

) : Parcelable