package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
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
                    pickUpPlanet = PlanetStop("Dagobah"),
                    dropOffPlanet = PlanetStop("Coruscent")
                ),
                Trip(
                    id = 2,
                    pilot = Pilot("Darth Vader", "absolutePath/relative/url", 1.3f),
                    pickUpPlanet = PlanetStop("Dagobah"),
                    dropOffPlanet = PlanetStop("Coruscent")
                )
            )
        )
    }

    @Test
    fun `transformJsonTripListToDomainModel when pilot rating is zero should transform to null`() {
        // Given
        val jsonTrip = createJsonTripMock(tripId = 1, pilotRating = 0f)

        // When
        val tripList = tripListTransformer.transformJsonTripListToDomainModel(listOf(jsonTrip))

        // Then
        assertThat(tripList.first().pilot.rating).isNull()
    }

    private fun createJsonTripMock(tripId: Int, pilotRating: Float = 1.3f): JsonTrip {
        val pilot = mock<JsonPilot> {
            on { name } doReturn "Darth Vader"
            on { rating } doReturn pilotRating
            on { avatarUrl } doReturn "/relative/url"
        }
        val pickUpPlanetStop = mock<JsonPlanetStop> {
            on { name } doReturn "Dagobah"
        }
        val dropOffPlanetStop = mock<JsonPlanetStop> {
            on { name } doReturn "Coruscent"
        }
        return mock {
            on { this.id } doReturn tripId
            on { this.pilot } doReturn pilot
            on { this.pickUpPlanetStop } doReturn pickUpPlanetStop
            on { this.dropOffPlanetStop } doReturn dropOffPlanetStop
        }
    }
}