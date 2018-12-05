package com.remipradal.starwars.core.triplist

interface TripListPresenter {

    fun presentLoader(loaderState: LoaderState)

    fun presentTripList(tripList: List<Trip>)

    enum class LoaderState { DISPLAYED, HIDDEN }
}