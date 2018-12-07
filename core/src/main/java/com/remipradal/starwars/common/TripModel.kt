package com.remipradal.starwars.common

import org.joda.time.DateTime

data class Trip(
    val id: Int,
    val pilot: Pilot,
    val pickUpPlanet: PlanetStop,
    val dropOffPlanet: PlanetStop
)

data class Pilot(
    val name: String,
    val avatarUrl: String,
    val rating: Float?
)

data class PlanetStop(
    val name: String,
    val imageUrl: String,
    val passageDateTime: DateTime
)