package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.common.JsonPilot
import com.remipradal.starwars.common.JsonPlanetStop
import com.remipradal.starwars.common.JsonTrip
import com.remipradal.starwars.common.Pilot
import com.remipradal.starwars.common.PlanetStop
import com.remipradal.starwars.common.Trip
import com.remipradal.starwars.common.TripListTransformer
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test

class TripListTransformerTest {

    private val tripListTransformer = TripListTransformer("absolutePath")

    @Test
    fun `transformJsonTripListToDomainModel should map to domain data`() {
        // Given
        val firstJsonTrip = createJsonTripMock(tripId = 1)
        val secondJsonTrip = createJsonTripMock(tripId = 2)
        val jsonTripList = listOf(firstJsonTrip, secondJsonTrip)

        // When
        val tripList = tripListTransformer.transformJsonTripListToDomainModel(jsonTripList)

        // Then
        assertThat(tripList).isEqualTo(
            listOf(
                Trip(
                    id = 1,
                    pilot = Pilot("Darth Vader", "absolutePath/relative/url", 1.3f),
                    pickUpPlanet = PlanetStop(
                        name = "Dagobah",
                        imageUrl = "absolutePath/planet1/url",
                        passageDateTime = DateTime.parse("2018-12-8")
                    ),
                    dropOffPlanet = PlanetStop(
                        name = "Coruscent",
                        imageUrl = "absolutePath/planet2/url",
                        passageDateTime = DateTime.parse("2018-12-9")
                    )
                ),
                Trip(
                    id = 2,
                    pilot = Pilot("Darth Vader", "absolutePath/relative/url", 1.3f),
                    pickUpPlanet = PlanetStop(
                        name = "Dagobah",
                        imageUrl = "absolutePath/planet1/url",
                        passageDateTime = DateTime.parse("2018-12-8")
                    ),
                    dropOffPlanet = PlanetStop(
                        name = "Coruscent",
                        imageUrl = "absolutePath/planet2/url",
                        passageDateTime = DateTime.parse("2018-12-9")
                    )
                )
            )
        )
    }

    @Test
    fun `transformJsonTripToDomainModel should map to domain data`() {
        // Given
        val jsonTrip = createJsonTripMock(tripId = 1)

        // When
        val trip = tripListTransformer.transformJsonTripToDomainModel(jsonTrip)

        // Then
        assertThat(trip).isEqualTo(
            Trip(
                id = 1,
                pilot = Pilot("Darth Vader", "absolutePath/relative/url", 1.3f),
                pickUpPlanet = PlanetStop(
                    name = "Dagobah",
                    imageUrl = "absolutePath/planet1/url",
                    passageDateTime = DateTime.parse("2018-12-8")
                ),
                dropOffPlanet = PlanetStop(
                    name = "Coruscent",
                    imageUrl = "absolutePath/planet2/url",
                    passageDateTime = DateTime.parse("2018-12-9")
                )
            )
        )
    }

    @Test
    fun `transformJsonTripToDomainModel when pilot rating is zero should transform to null`() {
        // Given
        val jsonTrip = createJsonTripMock(tripId = 1, pilotRating = 0f)

        // When
        val trip = tripListTransformer.transformJsonTripToDomainModel(jsonTrip)

        // Then
        assertThat(trip.pilot.rating).isNull()
    }

    private fun createJsonTripMock(tripId: Int, pilotRating: Float = 1.3f): JsonTrip {
        val pilot = mock<JsonPilot> {
            on { name } doReturn "Darth Vader"
            on { rating } doReturn pilotRating
            on { avatarUrl } doReturn "/relative/url"
        }
        val pickUpPlanetStop = mock<JsonPlanetStop> {
            on { name } doReturn "Dagobah"
            on { pictureUrl } doReturn "/planet1/url"
            on { passageDateTime } doReturn DateTime.parse("2018-12-8")
        }
        val dropOffPlanetStop = mock<JsonPlanetStop> {
            on { name } doReturn "Coruscent"
            on { pictureUrl } doReturn "/planet2/url"
            on { passageDateTime } doReturn DateTime.parse("2018-12-9")
        }
        return mock {
            on { this.id } doReturn tripId
            on { this.pilot } doReturn pilot
            on { this.pickUpPlanetStop } doReturn pickUpPlanetStop
            on { this.dropOffPlanetStop } doReturn dropOffPlanetStop
        }
    }
}