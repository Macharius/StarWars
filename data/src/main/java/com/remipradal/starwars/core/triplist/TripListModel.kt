package com.remipradal.starwars.core.triplist

import com.squareup.moshi.Json
import org.joda.time.DateTime

data class JsonTrip(
    @Json(name = "id") val id: Int,
    @Json(name = "pilot") val pilot: JsonPilot,
    @Json(name = "distance") val distance: JsonDistance,
    @Json(name = "duration") val duration: Int,
    @Json(name = "pick_up") val pickUpPlanetStop: JsonPlanetStop,
    @Json(name = "drop_off") val dropOffPlanetStop: JsonPlanetStop
)

data class JsonPilot(
    @Json(name = "name") val name: String,
    @Json(name = "avatar") val avatarUrl: String,
    @Json(name = "rating") val rating: Float
)

data class JsonDistance(
    @Json(name = "value") val value: Long,
    @Json(name = "unit") val unit: JsonDistanceUnit
)

data class JsonPlanetStop(
    @Json(name = "name") val name: String,
    @Json(name = "picture") val pictureUrl: String,
    @Json(name = "date") val passageDateTime: DateTime
)

enum class JsonDistanceUnit {
    @Json(name = "km")
    KILOMETERS
}