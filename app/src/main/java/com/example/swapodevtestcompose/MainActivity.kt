package com.example.swapodevtestcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swapidevtest.DOMAIN.model.Person
import com.example.swapidevtest.DOMAIN.model.StarShips
import com.example.swapodevtestcompose.ui.theme.SwapoDevTestComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwapoDevTestComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    People()
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun People(peopleViewModel: PeopleViewModel = hiltViewModel()) {


    val expandedState = remember { mutableStateListOf<Boolean>() }
    Column() {


        SearchBar(
            onSearch = { peopleViewModel.getCombineList(it) }
        )

        Spacer(modifier = Modifier)
        Button(onClick = { /*TODO*/ }) {
            
        }

        val combineListFlow by peopleViewModel.searchCombineList.collectAsStateWithLifecycle()

        MultipleObjectsList(
            combineListFlow = combineListFlow,
            peopleViewModel,
            expandedState
        ) { person, list ->
            peopleViewModel.putPersonInDB(entity = person as Person, list = list)
        }

    }
}


@Composable
fun MultipleObjectsList(
    combineListFlow: List<Any>,
    peopleViewModel: PeopleViewModel,
    expandedState: SnapshotStateList<Boolean>,
    onclick: (Any, List<String>) -> Unit
) {

    combineListFlow.forEach { alementOfList ->
        expandedState.add(false)

    }

    LazyColumn {


        itemsIndexed(combineListFlow) { index, item ->
            when (item) {
                is Person -> CreateOneElementofLazyColumn(
                    index = index,
                    item = item,
                    expandedState = expandedState,
                    peopleViewModel,
                    onclick
                )

                is StarShips -> Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Text(
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(1f),
                        text = item.name,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(1f),
                        text = item.model,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(1f), text = item.manufacturer,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(1f),
                        text = item.passengers,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }

    }
}

@Composable
fun CreateOneElementofLazyColumn(
    index: Int,
    item: Person,
    expandedState: SnapshotStateList<Boolean>,
    peopleViewModel: PeopleViewModel,
    onclick: (Any, MutableList<String>) -> Unit
) {
    val listOfFilmResponse =
        peopleViewModel.getFilmsByList(item.films.toList() as MutableList<String>)
    val listOfFilms = mutableListOf<String>()
    for (film in listOfFilmResponse) {
        listOfFilms.add(film.title)
    }

    Row(
        modifier = Modifier.padding(15.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {


        Row(modifier = Modifier.clickable {
            expandedState[index] = !expandedState[index]

        }) {
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f),
                text = item.name
            )
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f), text = item.gender
            )
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f),
                text = item.starships.size.toString()
            )
//
            Button(onClick = {
                onclick(item, listOfFilms)
            }) {

            }

            if (expandedState[index]) {
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    for (film in listOfFilmResponse) {
                        Text(text = film.title)
                    }

                }
            }

        }

    }

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row() {
        TextField(
            value = text,
            onValueChange = {
                text = it
                if (it.length > 1) {
                    onSearch(it)

                }
            },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(text)
                // Hide the keyboard after submitting the search
                keyboardController?.hide()
                //or hide keyboard
                focusManager.clearFocus()

            })
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwapoDevTestComposeTheme {
        Greeting("Android")
    }
}