package com.remipradal.starwars.triplist

import com.remipradal.starwars.common.JsonTrip
import com.remipradal.starwars.common.Trip
import com.remipradal.starwars.common.TripListTransformer
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

