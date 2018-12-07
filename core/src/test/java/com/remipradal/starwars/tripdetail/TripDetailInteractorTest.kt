package com.remipradal.starwars.tripdetail

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.common.Trip
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripDetailInteractorTest {

    @InjectMocks private lateinit var tripDetailInteractor: TripDetailInteractor
    @Mock private lateinit var tripDetailRepository: TripDetailRepository

    @Test
    fun `getTripList should return `() {
        // Given
        val expectedTrip = mock<Single<Trip>>()
        given(tripDetailRepository.getTripDetail(42)).willReturn(expectedTrip)

        // When
        val trip = tripDetailInteractor.getTripDetail(42)

        // then
        assertThat(trip).isEqualTo(expectedTrip)
    }

}