package com.remipradal.starwars.core.triplist

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Inject

class TripListRepositoryImpl @Inject constructor(
    private val service: Service,
    private val tripListTransformer: TripListTransformer
) : TripListRepository {

    interface Service {
        @GET("trips")
        @Headers("Accept: application/json")
        fun getTripList(): Single<List<JsonTrip>>
    }

    override fun getTripList(): Single<List<Trip>> {
        return service.getTripList().map { tripListTransformer.transformJsonTripListToDomainModel(it) }
    }

}
