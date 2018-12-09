package com.remipradal.starwars.tripdetail

import android.content.res.Resources
import com.remipradal.starwars.R
import com.remipradal.starwars.common.Distance
import com.remipradal.starwars.common.DistanceUnit
import com.remipradal.starwars.common.RatingViewModelTransformer
import com.remipradal.starwars.common.Trip
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.PeriodFormatterBuilder
import java.text.NumberFormat
import javax.inject.Inject

class TripDetailModelToViewModelTransformer @Inject constructor(
    private val resources: Resources,
    private val timeFormatter: TimeFormatter,
    private val numberFormatter: NumberFormatter,
    private val ratingViewModelTransformer: RatingViewModelTransformer
) {

    fun transform(trip: Trip) = with(trip) {
        TripDetailViewModel(
            pilotName = pilot.name.toUpperCase(),
            pilotAvatarUrl = pilot.avatarUrl,
            pilotRatingViewModel = ratingViewModelTransformer.transformToRatingViewModel(pilot.rating),
            pickUpPlanetName = pickUpPlanet.name.toUpperCase(),
            pickUpPassageHour = timeFormatter.transformToString(pickUpPlanet.passageDateTime),
            pickUpPlanetImageUrl = pickUpPlanet.imageUrl,
            dropOffPlanetName = dropOffPlanet.name.toUpperCase(),
            dropOffPassageHour = timeFormatter.transformToString(dropOffPlanet.passageDateTime),
            dropOffPlanetImageUrl = dropOffPlanet.imageUrl,
            tripDistance = distance.transformToString(),
            tripDuration = timeFormatter.transformToString(Duration.millis(trip.durationMilliSeconds))
        )
    }

    private fun Distance.transformToString(): String {
        return StringBuilder()
            .append(numberFormatter.format(value))
            .append(' ')
            .append(
                when (unit) {
                    DistanceUnit.KILOMETERS -> resources.getString(R.string.unit_kilometers).toUpperCase()
                }
            )
            .toString()

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