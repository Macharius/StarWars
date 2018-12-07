package com.remipradal.starwars.tripdetail

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named


interface TripDetailDisplay {
    fun displayTripDetail(tripDetailViewModel: TripDetailViewModel)
    fun showLoader()
    fun hideLoader()
    fun displayError()
}

class TripDetailPresenter @Inject constructor(
    private val tripDetailInteractor: TripDetailInteractor,
    private val transformer: TripDetailModelToViewModelTransformer,
    @Named("WORKER") private val workerScheduler: Scheduler,
    @Named("UI") private val uiScheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) {

    private var tripDetailDisplay: TripDetailDisplay? = null

    fun subscribe(
        tripDetailDisplay: TripDetailDisplay,
        tripId: Int
    ) {
        this.tripDetailDisplay = tripDetailDisplay
        fetchTripDetail(tripId)
    }

    fun unsubscribe() {
        tripDetailDisplay = null
        compositeDisposable.clear()
    }

    private fun fetchTripDetail(tripId: Int) {
        tripDetailInteractor.getTripDetail(tripId)
                .subscribeOn(workerScheduler)
                .map(transformer::transform)
                .observeOn(uiScheduler)
                .doOnSubscribe { tripDetailDisplay?.showLoader() }
                .doFinally { tripDetailDisplay?.hideLoader() }
                .subscribe(
                    {
                        tripDetailDisplay?.displayTripDetail(it)
                    }, {
                        tripDetailDisplay?.displayError()
                    }
                )
                .let { compositeDisposable.addAll(it) }
    }

}