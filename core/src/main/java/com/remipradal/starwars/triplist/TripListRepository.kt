package com.remipradal.starwars.triplist

import io.reactivex.Single

interface TripListRepository {
    fun getTripList(): Single<List<Trip>>
}

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
    val name: String
)