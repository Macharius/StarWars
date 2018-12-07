package com.remipradal.starwars.triplist

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
        tripListInteractor.getTripList()
            .subscribeOn(workerScheduler)
            .map(transformer::transform)
            .observeOn(uiScheduler)
            .doOnSubscribe { tripListDisplay?.showLoader() }
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