package com.example.swapodevtestcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapidevtest.DATA.Repository.Repository
import com.example.swapidevtest.DOMAIN.model.FilmResponse
import com.example.swapidevtest.DOMAIN.model.PeopleSearchResponse
import com.example.swapidevtest.DOMAIN.model.Person
import com.example.swapidevtest.DOMAIN.model.StarShips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    val repository: Repository,
) : ViewModel() {


    val searchPeopleFlow: StateFlow<List<Person>> get() = _searchPeople
    private var _searchPeople: MutableStateFlow<List<Person>> =
        MutableStateFlow(listOf())


    val searchStarShipsFlow: StateFlow<List<StarShips>> get() = _searchStarShips
    private var _searchStarShips: MutableStateFlow<List<StarShips>> =
        MutableStateFlow(listOf())

    val searchCombineList: StateFlow<List<Any>> get() = _searchCombineList
    private var _searchCombineList: MutableStateFlow<List<Any>> =
        MutableStateFlow(listOf())

    fun getCombineList(qwerty: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val people = repository.searchPeopleInInternet(qwerty)
            val starships = repository.searchStarshipsInInternet(qwerty)

            people.zip(starships) { people, starShips ->
                val peopleStarhipsList = mutableListOf<Any>()
                peopleStarhipsList.addAll(people)
                peopleStarhipsList.addAll(starShips)
                println("!!! $peopleStarhipsList")
                return@zip peopleStarhipsList


            }.flowOn(Dispatchers.IO)
                .catch { e -> println("ERROR + $e") }
                .collect {
                    _searchCombineList.value = it

                }
        }
    }

    fun getFilms(entity: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<FilmResponse>()
            for (film in entity.films) {

                val id = film.filter {
                    it.isDigit()
                }
                val film=repository.getFilm(id)
                list.add(film)
            }
            println("@@@"+list[0].title.toString())


        }

    }
//    fun addListOfFilmsToEntity(list: MutableList<FilmResponse>)

    fun getPeopleFromApi(qwerty: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchPeopleInInternet(qwerty)
                .collect {
                    it.let {
                        _searchPeople.value = it
                    }
                }
        }
    }

    fun getStarShipsFromApi(qwerty: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchStarshipsInInternet(qwerty)
                .collect {
                    it.let {
                        _searchStarShips.value = it
                    }
                }
        }
    }


}



