package com.remipradal.starwars.tripdetail

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.common.JsonTrip
import com.remipradal.starwars.common.Trip
import com.remipradal.starwars.common.TripListTransformer
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripDetailRepositoryImplTest {

    @InjectMocks private lateinit var tripDetailRepositoryImpl: TripDetailRepositoryImpl
    @Mock private lateinit var service: TripDetailRepositoryImpl.Service
    @Mock private lateinit var tripListTransformer: TripListTransformer

    @Test
    fun `getTripDetail should call service and map result to domain model`() {
        // Given
        val jsonTrip = mock<JsonTrip>()
        val jsonTripSingle = Single.just(jsonTrip)
        given(service.getTripDetail(42)).willReturn(jsonTripSingle)
        val expectedTrip = mock<Trip>()
        given(tripListTransformer.transformJsonTripToDomainModel(jsonTrip)).willReturn(expectedTrip)

        // When
        val tripListResponse = tripDetailRepositoryImpl.getTripDetail(42)

        // Then
        tripListResponse.test().assertResult(expectedTrip)
    }
}