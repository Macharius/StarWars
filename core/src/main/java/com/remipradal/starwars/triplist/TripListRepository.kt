package com.remipradal.starwars.triplist

import com.remipradal.starwars.common.Trip
import io.reactivex.Single

interface TripListRepository {
    fun getTripList(): Single<List<Trip>>
}