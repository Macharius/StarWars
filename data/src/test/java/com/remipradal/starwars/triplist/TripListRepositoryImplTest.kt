package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.remipradal.starwars.common.JsonTrip
import com.remipradal.starwars.common.TripListTransformer
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripListRepositoryImplTest {

    @InjectMocks private lateinit var tripListRepositoryImpl: TripListRepositoryImpl
    @Mock private lateinit var service: TripListRepositoryImpl.Service
    @Mock private lateinit var tripListTransformer: TripListTransformer

    @Test
    fun `getTripList should call service and map result to domain model`() {
        // Given
        val element = mock<List<JsonTrip>>()
        val jsonTripList = Single.just(element)
        given(service.getTripList()).willReturn(jsonTripList)
        val expectedTripList = mock<List<Trip>>()
        given(tripListTransformer.transformJsonTripListToDomainModel(element)).willReturn(expectedTripList)

        // When
        val tripListResponse = tripListRepositoryImpl.getTripList()

        // Then
        tripListResponse.test().assertResult(expectedTripList)
    }

}