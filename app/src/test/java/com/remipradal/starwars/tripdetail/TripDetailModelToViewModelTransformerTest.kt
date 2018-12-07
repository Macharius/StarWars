package com.remipradal.starwars.tripdetail

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.common.*
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.PeriodType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripDetailModelToViewModelTransformerTest {

    @InjectMocks
    private lateinit var tripDetailModelToViewModelTransformer: TripDetailModelToViewModelTransformer
    @Mock
    private lateinit var timeFormatter: TimeFormatter
    @Mock
    private lateinit var numberFormatter: NumberFormatter

    @Before
    fun setUp() {
        given(timeFormatter.transformToString(any<DateTime>())).willReturn("6:06")
        given(timeFormatter.transformToString(any<Period>())).willReturn("10:10")

        given(numberFormatter.format(2478572)).willReturn("2,478,572")
    }

    @Test
    fun `transform should transform pilot name`() {
        // Given
        val trip = createMockTrip()

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.pilotName).isEqualTo("DARTH VADER")
    }

    @Test
    fun `transform should map pilot avatar url`() {
        // Given
        val trip = createMockTrip()

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.pilotAvatarUrl).isEqualTo("avatarUrl")
    }

    @Test
    fun `transform should transform pick up planet name`() {
        // Given
        val trip = createMockTrip()

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.pickUpPlanetName).isEqualTo("DAGOBAH")
    }

    @Test
    fun `transform should transform pick up planet date time`() {
        // Given
        val trip = createMockTrip()
        given(timeFormatter.transformToString(DateTime.parse("2017-12-09T14:12:51Z"))).willReturn("14:12")

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.pickUpPassageHour).isEqualTo("14:12")
    }

    fun `transform should transform drop off planet name`() {
        // Given
        val trip = createMockTrip()

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.pickUpPlanetName).isEqualTo("CORUSCENT")
    }

    @Test
    fun `transform should transform drop off planet date time`() {
        // Given
        val trip = createMockTrip()
        given(timeFormatter.transformToString(DateTime.parse("2017-12-10T14:12:51Z"))).willReturn("10:12")

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.dropOffPassageHour).isEqualTo("10:12")
    }

    @Test
    fun `transform should transform trip duration`() {
        // Given
        val trip = createMockTrip()
        given(timeFormatter.transformToString(Period(19427000, PeriodType.millis()))).willReturn("5:03:12")

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.tripDuration).isEqualTo("5:03:12")
    }

    @Test
    fun `transform should transform trip distance`() {
        // Given
        val trip = createMockTrip()

        // When
        val tripDetailViewModel = tripDetailModelToViewModelTransformer.transform(trip)

        // Then
        assertThat(tripDetailViewModel.tripDistance).isEqualTo("2,478,572 KM")
    }

    private fun createMockTrip(
        pilotName: String = "Darth Vader",
        pilotAvatarUrl: String = "avatarUrl",
        pickUpPlanetName: String = "Dagobah",
        pickUpPassageDateTime: DateTime = DateTime.parse("2017-12-09T14:12:51Z"),
        dropOffPlanetName: String = "Coruscent",
        dropOffPassageHour: DateTime = DateTime.parse("2017-12-10T14:12:51Z"),
        tripDistanceKilometers: Long = 2478572,
        tripDurationMilliSeconds: Long = 19427000
    ): Trip {
        val pilot = mock<Pilot> {
            on { this.avatarUrl } doReturn pilotAvatarUrl
            on { this.name } doReturn pilotName
        }

        val pickUpPlanet = mock<PlanetStop> {
            on { this.name } doReturn pickUpPlanetName
            on { this.passageDateTime } doReturn pickUpPassageDateTime
        }

        val dropOffPlanet = mock<PlanetStop> {
            on { this.name } doReturn dropOffPlanetName
            on { this.passageDateTime } doReturn dropOffPassageHour
        }

        val tripDistance = mock<Distance> {
            on { this.value } doReturn tripDistanceKilometers
        }

        return mock {
            on { this.pilot } doReturn pilot
            on { this.dropOffPlanet } doReturn dropOffPlanet
            on { this.pickUpPlanet } doReturn pickUpPlanet
            on { this.durationMilliSeconds } doReturn tripDurationMilliSeconds
            on { this.distance } doReturn tripDistance
        }
    }

}