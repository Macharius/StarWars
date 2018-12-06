package com.remipradal.starwars.core.triplist

import io.reactivex.Single
import javax.inject.Inject

class TripListInteractor @Inject constructor(
    private val tripListRepository: TripListRepository
) {
    fun getTripList(): Single<List<Trip>> {
        return tripListRepository.getTripList()
    }
}