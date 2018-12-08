package com.remipradal.starwars.tripdetail

import com.remipradal.starwars.common.JsonTrip
import com.remipradal.starwars.common.Trip
import com.remipradal.starwars.common.TripListTransformer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import javax.inject.Inject

class NetworkTripDetailRepository @Inject constructor(
    private val service: Service,
    private val tripListTransformer: TripListTransformer
) : TripDetailRepository {

    interface Service {
        @GET("trips/{id}")
        @Headers("Accept: application/json")
        fun getTripDetail(@Path("id") id: Int): Single<JsonTrip>
    }

    override fun getTripDetail(id: Int): Single<Trip> {
        return service.getTripDetail(id).map(tripListTransformer::transformJsonTripToDomainModel)
    }
}