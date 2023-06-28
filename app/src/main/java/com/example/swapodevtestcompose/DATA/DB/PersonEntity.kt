package com.example.swapidevtest.DATA.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.swapidevtest.DATA.DB.Constants.PERSON_TABLE
import com.example.swapidevtest.DOMAIN.model.ListConverter.ArrayListConverter
import com.example.swapidevtest.DOMAIN.model.Person

@TypeConverters(ArrayListConverter::class)
@Entity(tableName = PERSON_TABLE)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val personId: Int = 0,
    var name: String = "",
    @ColumnInfo(name = "sex")
    var sex: String = "",
    @ColumnInfo(name = "star_ship")
    var starShip: ArrayList<String> = arrayListOf(),
    @ColumnInfo(name = "films")
    var films: ArrayList<String> = arrayListOf()
)

fun PersonEntity.personToPersonEntity(person: Person): PersonEntity {

    this.name = person.name
    this.sex = person.gender
    this.starShip = ArrayList(person.starships)
    this.films = ArrayList(person.films)
    println(this.name)
    return this

}