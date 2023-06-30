package com.example.swapidevtest.DATA.Repository

import android.util.Log
import com.example.swapidevtest.DATA.DB.PersonDao
import com.example.swapidevtest.DATA.DB.PersonEntity
import com.example.swapidevtest.DOMAIN.model.PeopleSearchResponse
import com.example.swapidevtest.DOMAIN.model.Person
import com.example.swapidevtest.DOMAIN.model.StarShips
import com.example.ui.DATA.Api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class Repository @Inject constructor(
    private val personDao: PersonDao,
    private val apiService: ApiService
) {
    suspend fun getPeopleFromInternet(qwerty: String?): Flow<PeopleSearchResponse> {
        return flow {
            val response = apiService.getPeopleSearch(qwerty)
            Log.d("*response", response.toString())
            emit(response)
        }.flowOn(Dispatchers.IO)

    }


//    suspend fun searchPeopleAndStarshipsInInternet(query: String?): Flow<List<Any>>

        suspend fun searchPeopleInInternet(qwerty: String) = flow<List<Person>> {
            val response = apiService.getPeopleSearch(qwerty)
//            println("*response"+ response.toString())
            response.people?.let {

//                println("****** PERSON LIST"+ it)
                emit(it)
            }
        }.flowOn(Dispatchers.IO)

        fun searchStarshipsInInternet(qwerty:String) = flow<List<StarShips>> {
            val response = apiService.getStarhipsSearch(qwerty)

            response.let {
//                println("****** STARSHIP LIST" + it)
                emit(it.results)
            }

        }.flowOn(Dispatchers.IO)




    suspend fun getPeopleFromInternetInStateFlow(
        scoped: CoroutineScope,
        qwerty: String?
    ): StateFlow<PeopleSearchResponse> {
        val a = flow {
            val response = apiService.getPeopleSearch(qwerty)
            Log.d("*response", response.toString())
            emit(response)
        }.flowOn(Dispatchers.IO)
        return a.stateIn(scoped, SharingStarted.WhileSubscribed(600), PeopleSearchResponse())

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
