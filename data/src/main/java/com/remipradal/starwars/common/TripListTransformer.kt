package com.remipradal.starwars.common

import javax.inject.Inject

class TripListTransformer @Inject constructor(private val baseUrl: String) {

    fun transformJsonTripListToDomainModel(jsonTripList: List<JsonTrip>): List<Trip> {
        return jsonTripList.map { it.toTrip() }
    }

    fun transformJsonTripToDomainModel(jsonTrip: JsonTrip): Trip = jsonTrip.toTrip()

    private fun JsonPilot.toPilot() = Pilot(
        name = name,
        avatarUrl = baseUrl + avatarUrl,
        rating = if (rating != 0f) rating else null
    )

    private fun JsonPlanetStop.toPlanetStop() = PlanetStop(
        name = name,
        imageUrl = baseUrl + pictureUrl,
        passageDateTime = passageDateTime
    )

    private fun JsonTrip.toTrip() = Trip(
        id = id,
        pilot = pilot.toPilot(),
        pickUpPlanet = pickUpPlanetStop.toPlanetStop(),
        dropOffPlanet = dropOffPlanetStop.toPlanetStop(),
        distance = distance.toDistance(),
        durationMilliSeconds = duration
    )

    private fun JsonDistance.toDistance() = Distance(
        value = value,
        unit = DistanceUnit.KILOMETERS
    )

}