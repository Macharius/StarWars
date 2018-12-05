package com.remipradal.starwars.core.triplist

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class TripListRepositoryImplTest {

    @InjectMocks private lateinit var tripListRepositoryImpl: TripListRepositoryImpl
    @Mock private lateinit var service: TripListRepositoryImpl.Service
    @Mock private lateinit var tripListTransformer: TripListTransformer

    @Mock private lateinit var networkCall: Call<List<JsonTrip>>

    @Test
    fun `getTripList when network call throws IOException then should return Error`() {
        // Given
        given(service.getTripList()).willReturn(networkCall)
        given(networkCall.execute()).willThrow(IOException::class.java)

        // When
        val tripListResponse = tripListRepositoryImpl.getTripList()

        // Then
        assertThat(tripListResponse).isEqualTo(TripListResponse.Error)
    }

    @Test
    fun `getTripList when network result is not successful then should return Error`() {
        // Given
        given(service.getTripList()).willReturn(networkCall)
        given(networkCall.execute()).willReturn(Response.error(500, mock()))

        // When
        val tripListResponse = tripListRepositoryImpl.getTripList()

        // Then
        assertThat(tripListResponse).isEqualTo(TripListResponse.Error)
    }

    @Test
    fun `getTripList when network result is successful but body is null then should return Error`() {
        // Given
        given(service.getTripList()).willReturn(networkCall)
        given(networkCall.execute()).willReturn(Response.success(null))

        // When
        val tripListResponse = tripListRepositoryImpl.getTripList()

        // Then
        assertThat(tripListResponse).isEqualTo(TripListResponse.Error)
    }

    @Test
    fun `getTripList when network result is successful then should return transformed list`() {
        // Given
        given(service.getTripList()).willReturn(networkCall)
        val body = mock<List<JsonTrip>>()
        given(networkCall.execute()).willReturn(Response.success(body))
        val tripList = mock<List<Trip>>()
        given(tripListTransformer.transformJsonTripListToDomainModel(body)).willReturn(tripList)

        // When
        val tripListResponse = tripListRepositoryImpl.getTripList()

        // Then
        assertThat(tripListResponse).isEqualTo(TripListResponse.Data(tripList))
    }
}