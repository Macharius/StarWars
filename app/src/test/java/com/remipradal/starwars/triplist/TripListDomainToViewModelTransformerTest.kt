package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.common.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripListDomainToViewModelTransformerTest {

    @InjectMocks
    private lateinit var tripListDomainToViewModelTransformer: TripListDomainToViewModelTransformer
    @Mock
    private lateinit var ratingViewModelTransformer: RatingViewModelTransformer

    @Before
    fun setUp() {
        given(ratingViewModelTransformer.transformToRatingViewModel(any())).willReturn(mock())
    }

    @Test
    fun `transform should map domain model to view model`() {
        // Given
        val ratingViewModelMock = mock<RatingViewModel>()
        given(ratingViewModelTransformer.transformToRatingViewModel(1f)).willReturn(ratingViewModelMock)
        val firstTrip = createMockTrip(
            1,
            "avatar1",
            "Darth Vader",
            "Naboo",
            "Coruscent",
            1f
        )
        val secondTrip = createMockTrip(
            2,
            "avatar2",
            "Luke",
            "Terre",
            "Mars",
            1f
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
                    dropOffPlanetName = "Coruscent",
                    pilotRatingViewModel = ratingViewModelMock
                ),
                TripViewModel(
                    id = 2,
                    pilotAvatarUrl = "avatar2",
                    pilotName = "LUKE",
                    pickUpPlanetName = "Terre",
                    dropOffPlanetName = "Mars",
                    pilotRatingViewModel = ratingViewModelMock
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
            "Coruscent",
            2f
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
        dropOffPlanetName: String,
        rating: Float?
    ): Trip {

        val pilot = mock<Pilot> {
            on { this.avatarUrl } doReturn pilotAvatarUrl
            on { this.name } doReturn pilotName
            on { this.rating } doReturn rating
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