package com.remipradal.starwars.core.triplist

class TripListTransformer(private val baseUrl: String) {

    fun transformJsonTripListToDomainModel(jsonTripList: List<JsonTrip>): List<Trip> {
        return jsonTripList.map { it.toTrip() }
    }

    private fun JsonPilot.toPilot() = Pilot(
        name = name,
        avatarUrl = baseUrl + avatarUrl,
        rating = if (rating != 0f) rating else null
    )

    private fun JsonPlanetStop.toPlanetStop() = PlanetStop(name)

    private fun JsonTrip.toTrip() = Trip(
        id = id,
        pilot = pilot.toPilot(),
        pickUpPlanet = pickUpPlanetStop.toPlanetStop(),
        dropOffPlanet = dropOffPlanetStop.toPlanetStop()
    )

}