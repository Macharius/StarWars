package com.remipradal.starwars.core.triplist

class TripListTransformer {
    fun transformJsonTripListToDomainModel(jsonTripList: List<JsonTrip>): List<Trip> {
        return jsonTripList.map { Trip(it.id) }
    }
}