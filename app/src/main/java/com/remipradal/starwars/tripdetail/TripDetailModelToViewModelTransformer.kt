package com.remipradal.starwars.tripdetail

import com.remipradal.starwars.common.Trip
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class TripDetailModelToViewModelTransformer @Inject constructor(
    private val hourDateTimeFormatter: HourDateTimeFormatter
) {
    fun transform(trip: Trip) = with(trip) {
        TripDetailViewModel(
            pilotName = pilot.name.toUpperCase(),
            pilotAvatarUrl = pilot.avatarUrl,
            pickUpPlanetName = pickUpPlanet.name.toUpperCase(),
            pickUpPassageHour = hourDateTimeFormatter.transformToString(pickUpPlanet.passageDateTime),
            dropOffPlanetName = dropOffPlanet.name.toUpperCase(),
            dropOffPassageHour = hourDateTimeFormatter.transformToString(dropOffPlanet.passageDateTime),
            tripDistance = "foo",
            tripDuration = "foo"
        )
    }
}

class HourDateTimeFormatter @Inject constructor() {
    fun transformToString(dateTime: DateTime): String {
        return DateTimeFormat.shortTime().print(dateTime)
    }
}