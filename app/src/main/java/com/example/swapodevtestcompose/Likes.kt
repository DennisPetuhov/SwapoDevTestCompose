package com.example.swapodevtestcompose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun Likes(viewModel: PeopleViewModel = hiltViewModel()) {
    viewModel.getListPersonLiked()
    val listOfPersonEntity by viewModel.searchPeopleEntityFlow.collectAsStateWithLifecycle()

    Row() {
        LazyColumn() {
            items(listOfPersonEntity) { item ->
                Text(text = item.name)
                

            }

        }
    }


}