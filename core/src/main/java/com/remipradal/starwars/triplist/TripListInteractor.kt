package com.remipradal.starwars.triplist

import com.remipradal.starwars.common.Trip
import io.reactivex.Single
import javax.inject.Inject

class TripListInteractor @Inject constructor(
    private val tripListRepository: TripListRepository
) {
    fun getTripList(): Single<List<Trip>> {
        return tripListRepository.getTripList()
    }
}