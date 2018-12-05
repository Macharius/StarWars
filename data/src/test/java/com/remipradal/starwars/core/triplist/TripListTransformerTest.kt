package com.remipradal.starwars.core.triplist

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TripListTransformerTest {

    private val tripListTransformer = TripListTransformer()

    @Test
    fun `transformJsonTripListToDomainModel should map to domain data`() {
        // Given
        val firstJsonTrip = mock<JsonTrip> { on { id } doReturn 1 }
        val secondJsonTrip = mock<JsonTrip> { on { id } doReturn 2 }
        val jsonTripList = listOf(firstJsonTrip, secondJsonTrip)

        // When
        val tripList = tripListTransformer.transformJsonTripListToDomainModel(jsonTripList)

        // Then
        assertThat(tripList).isEqualTo(listOf(Trip(id = 1), Trip(id = 2)))
    }
}