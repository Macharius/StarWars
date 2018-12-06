package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.core.triplist.Pilot
import com.remipradal.starwars.core.triplist.PlanetStop
import com.remipradal.starwars.core.triplist.Trip
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripListDomainToViewModelTransformerTest {

    @InjectMocks private lateinit var tripListDomainToViewModelTransformer: TripListDomainToViewModelTransformer

    @Test
    fun `transform should map domain model to view model`() {
        val firstTrip = createMockTrip(
            1,
            "avatar1",
            "Darth Vader",
            "Naboo",
            "Coruscent"
        )
        val secondTrip = createMockTrip(
            2,
            "avatar2",
            "Luke",
            "Terre",
            "Mars"
        )

        // When
        val viewModelList = tripListDomainToViewModelTransformer.transform(listOf(firstTrip, secondTrip))

        // Then
        assertThat(viewModelList).isEqualTo(
            listOf(
                TripViewModel(
                    id = 1,
                    pilotAvatarUrl = "avatar1",
                    pilotName = "DARTH VADER",
                    pickUpPlanetName = "Naboo",
                    dropOffPlanetName = "Coruscent"
                ),
                TripViewModel(
                    id = 2,
                    pilotAvatarUrl = "avatar2",
                    pilotName = "LUKE",
                    pickUpPlanetName = "Terre",
                    dropOffPlanetName = "Mars"
                )
            )
        )
    }

    @Test
    fun `transform should capitalize pilot name`() {
        // Given
        val trip = createMockTrip(
            1,
            "avatar1",
            "Darth Vader",
            "Naboo",
            "Coruscent"
        )

        // When
        val viewModelList = tripListDomainToViewModelTransformer.transform(listOf(trip))

        // Then
        assertThat(viewModelList.first().pilotName).isEqualTo("DARTH VADER")
    }

    private fun createMockTrip(
        id: Int,
        pilotAvatarUrl: String,
        pilotName: String,
        pickUpPlanetName: String,
        dropOffPlanetName: String
    ): Trip {

        val pilot = mock<Pilot> {
            on { this.avatarUrl } doReturn pilotAvatarUrl
            on { this.name } doReturn pilotName
        }

        val pickUpPlanet = mock<PlanetStop> {
            on { this.name } doReturn pickUpPlanetName
        }

        val dropOffPlanet = mock<PlanetStop> {
            on { this.name } doReturn dropOffPlanetName
        }

        return mock {
            on { this.id } doReturn id
            on { this.pilot } doReturn pilot
            on { this.dropOffPlanet } doReturn dropOffPlanet
            on { this.pickUpPlanet } doReturn pickUpPlanet
        }
    }
}