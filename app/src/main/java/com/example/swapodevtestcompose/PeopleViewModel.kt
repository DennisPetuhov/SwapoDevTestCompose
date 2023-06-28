package com.example.swapodevtestcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapidevtest.DATA.Repository.PeopleRepository
import com.example.swapidevtest.DOMAIN.model.PeopleSearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(val repository: PeopleRepository) : ViewModel() {


    val searchPeopleFlow: StateFlow<PeopleSearchResponse> get() = _searchPeople
    private var _searchPeople: MutableStateFlow<PeopleSearchResponse> =
        MutableStateFlow(PeopleSearchResponse())

    fun getPeopleFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPeopleFromInternet("sky").collect {
                it?.let {
                    _searchPeople.value = it
                }
            }
        }
    }
}



