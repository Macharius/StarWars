package com.remipradal.starwars.tripdetail

import com.remipradal.starwars.common.Trip
import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.PeriodType
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
            tripDuration = timeFormatter.transformToString(Period(trip.durationMilliSeconds, PeriodType.millis()))
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

    fun transformToString(period: Period): String {
        return PeriodFormatterBuilder()
            .appendHours()
            .appendSeparator(":")
            .appendMinutes()
            .minimumPrintedDigits(2)
            .appendSeparator(":")
            .appendSeconds()
            .minimumPrintedDigits(2)
            .toFormatter()
            .print(period)
    }
}