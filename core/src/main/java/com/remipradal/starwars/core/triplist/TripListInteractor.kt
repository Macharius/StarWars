package com.remipradal.starwars.core.triplist

class TripListInteractor(
    private val tripListRepository: TripListRepository,
    private val tripListPresenter: TripListPresenter
) {
    fun fetchTripList() {
        tripListPresenter.presentLoader(TripListPresenter.LoaderState.DISPLAYED)
        val tripListResponse = tripListRepository.getTripList()
        tripListPresenter.presentLoader(TripListPresenter.LoaderState.HIDDEN)
        when (tripListResponse) {
            is TripListResponse.Error -> tripListPresenter.presentErrorMessage()
            is TripListResponse.Data -> tripListPresenter.presentTripList(tripListResponse.tripList)
        }
    }
}