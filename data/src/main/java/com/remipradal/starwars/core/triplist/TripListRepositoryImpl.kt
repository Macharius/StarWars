package com.remipradal.starwars.core.triplist

import retrofit2.Call
import retrofit2.http.GET
import java.io.IOException

class TripListRepositoryImpl(
    private val service: Service,
    private val tripListTransformer: TripListTransformer
) : TripListRepository {

    interface Service {
        @GET("trips")
        fun getTripList(): Call<List<JsonTrip>>

    }

    override fun getTripList(): TripListResponse {
        return try {
            val tripListResponse = service.getTripList().execute()

            val body = tripListResponse.body()
            if (tripListResponse.isSuccessful && body != null) {
                TripListResponse.Data(tripListTransformer.transformJsonTripListToDomainModel(body))
            } else {
                TripListResponse.Error
            }

        } catch (exception: IOException) {
            return TripListResponse.Error

        }
    }

}

