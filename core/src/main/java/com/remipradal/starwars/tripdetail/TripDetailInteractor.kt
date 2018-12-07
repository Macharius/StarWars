package com.remipradal.starwars.tripdetail

import com.remipradal.starwars.common.Trip
import io.reactivex.Single
import javax.inject.Inject

class TripDetailInteractor @Inject constructor(
    private val tripDetailRepository: TripDetailRepository
) {

    fun getTripDetail(tripId: Int): Single<Trip> {
        return tripDetailRepository.getTripDetail(tripId)
    }
}