package com.remipradal.starwars.core.triplist

interface TripListRepository {
    fun getTripList(): TripListResponse
}

sealed class TripListResponse {
    object Error : TripListResponse()
    data class Data(val tripList: List<Trip>) : TripListResponse()
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