package com.remipradal.starwars.triplist

import com.remipradal.starwars.core.triplist.TripListInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class TripListController @Inject constructor(
    private val tripListInteractor: TripListInteractor,
    @Named("WORKER") private val coroutineScope: CoroutineScope
) {

    fun fetchTripList() = coroutineScope.launch {
        tripListInteractor.fetchTripList()
    }

}