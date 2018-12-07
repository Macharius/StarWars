package com.remipradal.starwars.tripdetail

import com.remipradal.starwars.common.Trip
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.PeriodFormatterBuilder
import java.text.NumberFormat
import javax.inject.Inject

class TripDetailModelToViewModelTransformer @Inject constructor(
    private val timeFormatter: TimeFormatter,
    private val numberFormatter: NumberFormatter
) {
    fun transform(trip: Trip) = with(trip) {
        TripDetailViewModel(
            pilotName = pilot.name.toUpperCase(),
            pilotAvatarUrl = pilot.avatarUrl,
            pickUpPlanetName = pickUpPlanet.name.toUpperCase(),
            pickUpPassageHour = timeFormatter.transformToString(pickUpPlanet.passageDateTime),
            dropOffPlanetName = dropOffPlanet.name.toUpperCase(),
            dropOffPassageHour = timeFormatter.transformToString(dropOffPlanet.passageDateTime),
            tripDistance = "${numberFormatter.format(distance.value)} KM",
            tripDuration = timeFormatter.transformToString(Duration.millis(trip.durationMilliSeconds))
        )
    }
}

class NumberFormatter @Inject constructor() {
    fun format(number: Long): String {
        return NumberFormat.getNumberInstance().format(number)
    }
}

class TimeFormatter @Inject constructor() {

    fun transformToString(dateTime: DateTime): String {
        return DateTimeFormat.shortTime().print(dateTime)
    }

    fun transformToString(period: Duration): String {
        return PeriodFormatterBuilder()
            .printZeroAlways()
            .minimumPrintedDigits(1)
            .appendHours()
            .minimumPrintedDigits(2)
            .appendSeparator(":")
            .appendMinutes()
            .appendSeparator(":")
            .appendSeconds()
            .toFormatter()
            .print(period.toPeriod())
    }
}