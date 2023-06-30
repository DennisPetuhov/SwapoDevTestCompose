package com.example.swapidevtest.DOMAIN.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StarShipsResponse(
    val count: Int?=null,
    val next: String?=null,
    val previous: String?=null,
    val results: List<StarShips>
):Parcelable
@Parcelize
data class StarShips(
    val MGLT: String,
    val cargo_capacity: String,
    val consumables: String,
    val cost_in_credits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val hyperdrive_rating: String,
    val length: String,
    val manufacturer: String,
    val max_atmosphering_speed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    val starship_class: String,
    val url: String
):Parcelable