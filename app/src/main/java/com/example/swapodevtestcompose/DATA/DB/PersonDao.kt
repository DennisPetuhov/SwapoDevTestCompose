package com.example.swapidevtest.DATA.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.swapidevtest.DATA.DB.Constants.PERSON_TABLE
import com.example.swapidevtest.DOMAIN.model.Person

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(personEntity: PersonEntity)

    @Update
    fun updatePerson(personEntity: PersonEntity)

    @Delete
    fun deletePerson(personEntity: PersonEntity)

    @Query("SELECT * FROM $PERSON_TABLE ORDER BY personId DESC")
    fun getAllPersons() : MutableList<PersonEntity>

    @Query("SELECT * FROM $PERSON_TABLE WHERE personId  LIKE :id")
    fun getPerson(id : Int) : PersonEntity

}