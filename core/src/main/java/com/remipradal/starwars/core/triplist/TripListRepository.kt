package com.remipradal.starwars.core.triplist

interface TripListRepository {
    fun getTripList(): TripListResponse
}

sealed class TripListResponse {
    object Error : TripListResponse()
    data class Data(val tripList: List<Trip>) : TripListResponse()
}

data class Trip(
    val id: Int
)