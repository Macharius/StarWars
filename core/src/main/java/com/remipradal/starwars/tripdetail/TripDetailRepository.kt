package com.remipradal.starwars.tripdetail

import com.remipradal.starwars.triplist.Trip
import io.reactivex.Single

interface TripDetailRepository {
    fun getTripDetail(id: Int): Single<Trip>
}