package com.remipradal.starwars.triplist

import com.remipradal.starwars.core.triplist.Trip
import com.remipradal.starwars.core.triplist.TripListPresenter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Named


interface TripListDisplay {
    fun displayTripList(tripList: List<TripViewModel>)
}

interface DisplayHolder<T> {
    var display: T?
}

class TripListPresenterImpl @Inject constructor(
    @Named("UI") private val coroutineScope: CoroutineScope
) : TripListPresenter, DisplayHolder<TripListDisplay> {

    override var display: TripListDisplay? = null

    override fun presentLoader(loaderState: TripListPresenter.LoaderState) {
    }

    override fun presentTripList(tripList: List<Trip>) {

    }

    override fun presentErrorMessage() {
    }

}

data class TripViewModel(
    val id: Int,
    val pilotAvatarUrl: String,
    val pilotName: String,
    val pickUpPlanetName: String,
    val dropOffPlanetName: String
)