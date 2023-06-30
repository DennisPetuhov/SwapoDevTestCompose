package com.example.swapodevtestcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swapidevtest.DOMAIN.model.PeopleSearchResponse
import com.example.swapidevtest.DOMAIN.model.Person
import com.example.swapidevtest.DOMAIN.model.StarShips
import com.example.swapidevtest.DOMAIN.model.StarShipsResponse
import com.example.swapodevtestcompose.ui.theme.SwapoDevTestComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow


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


    peopleViewModel.getCombineList("s")

    val combineListFlow by peopleViewModel.searchCombineList.collectAsStateWithLifecycle()
    println("********* combineList" + combineListFlow.toString())

    MultipleObjectsList(combineListFlow = combineListFlow)


}


@Composable
fun MultipleObjectsList(combineListFlow: List<Any>) {

    LazyColumn {

        items(combineListFlow) { item ->
            when (item) {
                is Person -> Text(text = item.name)
                is StarShips -> Text(text = item.name)

            }
        }
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