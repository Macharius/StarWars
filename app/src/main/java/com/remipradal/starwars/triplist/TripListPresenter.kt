package com.remipradal.starwars.triplist

import com.remipradal.starwars.core.triplist.Trip
import com.remipradal.starwars.core.triplist.TripListInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named


interface TripListDisplay {
    fun displayTripList(tripList: List<TripViewModel>)
    fun showLoader()
    fun hideLoader()
    fun displayError()
}


class TripListPresenter @Inject constructor(
    private val tripListInteractor: TripListInteractor,
    private val transformer: TripListDomainToViewModelTransformer,
    @Named("WORKER") private val workerScheduler: Scheduler,
    @Named("UI") private val uiScheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) {
    private var tripListDisplay: TripListDisplay? = null

    fun subscribe(tripListDisplay: TripListDisplay) {
        this.tripListDisplay = tripListDisplay
        fetchTripList()
    }

    fun unsubscribe() {
        tripListDisplay = null
        compositeDisposable.clear()
    }

    private fun fetchTripList() {
        tripListDisplay?.showLoader()
        tripListInteractor.getTripList()
                .subscribeOn(workerScheduler)
                .map { transformer.transform(it) }
                .observeOn(uiScheduler)
                .doFinally { tripListDisplay?.hideLoader() }
                .subscribe(
                    {
                        tripListDisplay?.displayTripList(it)
                    }, {
                        tripListDisplay?.displayError()
                    }
                )
                .let { compositeDisposable.addAll(it) }
    }

}

class TripListDomainToViewModelTransformer @Inject constructor() {
    fun transform(tripList: List<Trip>): List<TripViewModel> {
        return tripList.map {
            TripViewModel(
                id = it.id,
                pilotAvatarUrl = it.pilot.avatarUrl,
                pilotName = it.pilot.name.toUpperCase(),
                dropOffPlanetName = it.dropOffPlanet.name,
                pickUpPlanetName = it.pickUpPlanet.name
            )
        }
    }

}

data class TripViewModel(
    val id: Int,
    val pilotAvatarUrl: String,
    val pilotName: String,
    val pickUpPlanetName: String,
    val dropOffPlanetName: String
)