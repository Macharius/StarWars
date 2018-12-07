package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.common.Pilot
import com.remipradal.starwars.common.PlanetStop
import com.remipradal.starwars.common.Trip
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
                    pilotRating = Rating.StarRating(
                        Rating.StarType.FILLED,
                        Rating.StarType.EMPTY,
                        Rating.StarType.EMPTY,
                        Rating.StarType.EMPTY,
                        Rating.StarType.EMPTY
                    )
                ),
                TripViewModel(
                    id = 2,
                    pilotAvatarUrl = "avatar2",
                    pilotName = "LUKE",
                    pickUpPlanetName = "Terre",
                    dropOffPlanetName = "Mars",
                    pilotRating = Rating.StarRating(
                        Rating.StarType.FILLED,
                        Rating.StarType.EMPTY,
                        Rating.StarType.EMPTY,
                        Rating.StarType.EMPTY,
                        Rating.StarType.EMPTY
                    )
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

    @Test
    fun `transform when rating is null should not show rating`() {
        // Given
        val trip = createMockTrip(
            1,
            "avatar1",
            "Darth Vader",
            "Naboo",
            "Coruscent",
            null
        )

        // When
        val viewModelList = tripListDomainToViewModelTransformer.transform(listOf(trip))

        // Then
        assertThat(viewModelList.first().pilotRating).isEqualTo(Rating.NoRating)
    }

    @Test
    fun `transform when rating is below 1 should have only empty stars`() {
        // Given
        val trip = createMockTrip(
            1,
            "avatar1",
            "Darth Vader",
            "Naboo",
            "Coruscent",
            0.9f
        )

        // When
        val viewModelList = tripListDomainToViewModelTransformer.transform(listOf(trip))

        // Then
        assertThat(viewModelList.first().pilotRating).isEqualTo(Rating.StarRating(
            Rating.StarType.EMPTY,
            Rating.StarType.EMPTY,
            Rating.StarType.EMPTY,
            Rating.StarType.EMPTY,
            Rating.StarType.EMPTY
        ))
    }

    @Test
    fun `transform when rating is above 1 but below 2 then should have a rating with only the first star filled`() {
        // Given
        val trip = createMockTrip(
            1,
            "avatar1",
            "Darth Vader",
            "Naboo",
            "Coruscent",
            1.1f
        )

        // When
        val viewModelList = tripListDomainToViewModelTransformer.transform(listOf(trip))

        // Then
        assertThat(viewModelList.first().pilotRating).isEqualTo(Rating.StarRating(
            Rating.StarType.FILLED,
            Rating.StarType.EMPTY,
            Rating.StarType.EMPTY,
            Rating.StarType.EMPTY,
            Rating.StarType.EMPTY
        ))
    }

    @Test
    fun `transform when rating is 5 then should have all stars filled`() {
        // Given
        val trip = createMockTrip(
            1,
            "avatar1",
            "Darth Vader",
            "Naboo",
            "Coruscent",
            5f
        )

        // When
        val viewModelList = tripListDomainToViewModelTransformer.transform(listOf(trip))

        // Then
        assertThat(viewModelList.first().pilotRating).isEqualTo(Rating.StarRating(
            Rating.StarType.FILLED,
            Rating.StarType.FILLED,
            Rating.StarType.FILLED,
            Rating.StarType.FILLED,
            Rating.StarType.FILLED
        ))
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