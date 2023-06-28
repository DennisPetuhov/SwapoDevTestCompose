package com.example.swapidevtest.DATA.Repository

import android.util.Log
import com.example.swapidevtest.DATA.DB.PersonDao
import com.example.swapidevtest.DATA.DB.PersonEntity
import com.example.swapidevtest.DOMAIN.model.PeopleSearchResponse
import com.example.ui.DATA.Api.ApiServicePeople
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val personDao: PersonDao,
    private val apiService: ApiServicePeople
) {
    suspend fun getPeopleFromInternet(qwerty:String?): Flow<PeopleSearchResponse> {
        return flow {
            val response = apiService.getPeopleSearch(qwerty)
            Log.d("*response", response.toString())
            emit(response)
        }.flowOn(Dispatchers.IO)

    }


    fun saveNote(note: PersonEntity) = personDao.insertPerson(note)
    fun updateNote(note: PersonEntity) = personDao.updatePerson(note)
    fun deleteNote(note: PersonEntity) = personDao.deletePerson(note)
    fun getNote(id: Int): PersonEntity = personDao.getPerson(id)
    fun getAllPeopleFromDB(): Flow<MutableList<PersonEntity>> {
        return flow {
            val list = personDao.getAllPersons()
            emit(list)
        }
    }
}
