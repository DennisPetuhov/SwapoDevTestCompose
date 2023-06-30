//package com.example.swapodevtestcompose
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import kotlinx.coroutines.flow.compose
//
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewModelScope
//import com.example.swapidevtest.DOMAIN.model.PeopleSearchResponse
//import dagger.hilt.android.scopes.ViewModelScoped
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.WhileSubscribed
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//
//// Define the data class for a person
//data class Person(val name: String, val age: Int)
//
//// Define the ViewModel for managing people data
//class PeopleViewModel1 : ViewModel() {
//    private val _searchPeopleFlow: MutableStateFlow<List<Person>> = MutableStateFlow(emptyList())
//
//    val searchPeopleFlow: StateFlow<List<Person>>
//        get() = _searchPeopleFlow
//
//    // Simulate loading people data
//    fun loadPeopleData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            // Simulated data
//            val peopleList = listOf(
//                Person("John", 25),
//                Person("Alice", 19),
//                Person("Bob", 30)
//            )
//
//            // Update the StateFlow with the loaded data
//            _searchPeopleFlow.value = peopleList
//        }
//    }
//}
//@Composable
//// Usage example
//fun main1() {
//    // Initialize the ViewModel
//    val peopleViewModel = PeopleViewModel1()
//
//    // Load people data
//    peopleViewModel.loadPeopleData()
//
//    // Collect the transformed StateFlow with lifecycle support
//    val peopleStateFlow = peopleViewModel.searchPeopleFlow.let {
//        originalFlow->
//        originalFlow.map { peopleList->
//            peopleList.filter {
//                it.age>18
//            }
//
//
//        }
//
//    }
////        .compose { originalFlow ->
////
////            }
////        }
//        .collectAsStateWithLifecycle("", LocalLifecycleOwner.current)
//
//
//
//    // Observe the StateFlow for changes
//    peopleStateFlow.observe { peopleList ->
//        // Handle updated peopleList
//        println(peopleList)
//    }
//
//    // Wait for the StateFlow updates
//    Thread.sleep(2000)
//}
//