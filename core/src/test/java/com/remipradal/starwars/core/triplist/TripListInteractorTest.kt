package com.remipradal.starwars.core.triplist

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripListInteractorTest {

    @InjectMocks private lateinit var tripListInteractor: TripListInteractor
    @Mock private lateinit var tripListRepository: TripListRepository

    @Test
    fun `getTripList should return `() {
        // Given
        val expectedTripList = mock<Single<List<Trip>>>()
        given(tripListRepository.getTripList()).willReturn(expectedTripList)

        // When
        val tripList = tripListInteractor.getTripList()

        // then
        assertThat(tripList).isEqualTo(expectedTripList)
    }

}