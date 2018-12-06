package com.remipradal.starwars.core.triplist

interface TripListPresenter {

    fun presentLoader(loaderState: LoaderState)

    fun presentTripList(tripList: List<Trip>)

    fun presentErrorMessage()

    enum class LoaderState { DISPLAYED, HIDDEN }
}