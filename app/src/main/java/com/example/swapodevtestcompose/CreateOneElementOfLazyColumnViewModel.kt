package com.example.swapodevtestcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapidevtest.DATA.Repository.Repository
import com.example.swapidevtest.DOMAIN.model.FilmResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CreateOneElementOfLazyColumnViewModel @Inject constructor(val repository: Repository):ViewModel() {





    val flow: StateFlow<List<String>> get() = _innerFlow
    private var _innerFlow: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())
    init {

    }

    fun produse(): Flow<List<String>> {
        val list = mutableListOf(
            mutableListOf<String>("8", "8", "8", "8"),
            mutableListOf<String>("7", "7", "7", "7")
        )
        return flow {
            for (i in list) {
                delay(6000)
                emit(i)
                println("*** $i")
            }

        }
    }

    fun recive() {
        viewModelScope.launch {
            produse().collect {
                _innerFlow.value = it
                println(it)
            }
        }
    }
}