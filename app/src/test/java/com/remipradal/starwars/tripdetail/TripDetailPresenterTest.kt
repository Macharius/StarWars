package com.remipradal.starwars.tripdetail

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import com.remipradal.starwars.common.Trip
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class TripDetailPresenterTest {

    @Mock private lateinit var tripDetailDisplay: TripDetailDisplay
    @Mock private lateinit var tripDetailInteractor: TripDetailInteractor
    @Mock private lateinit var transformer: TripDetailModelToViewModelTransformer
    @Mock private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var tripDetailPresenter: TripDetailPresenter

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        tripDetailPresenter = TripDetailPresenter(
            tripDetailInteractor,
            transformer,
            testScheduler,
            testScheduler,
            compositeDisposable
        )
    }

    @Test
    fun `subscribe when an error happens the should display error`() {
        // Given
        given(tripDetailInteractor.getTripDetail(42)).willReturn(Single.error(IOException()))

        // When
        tripDetailPresenter.subscribe(tripDetailDisplay, 42)
        testScheduler.triggerActions()

        // Then
        inOrder(tripDetailDisplay) {
            then(tripDetailDisplay).should(this).showLoader()
            then(tripDetailDisplay).should(this).displayError()
            then(tripDetailDisplay).should(this).hideLoader()
        }
    }

    @Test
    fun `subscribe when a trip list can be retrieved the should displayed it`() {
        // Given
        val tripDetail = mock<Trip>()
        given(tripDetailInteractor.getTripDetail(42)).willReturn(Single.just(tripDetail))
        val tripViewModelDetail = mock<TripDetailViewModel>()
        given(transformer.transform(tripDetail)).willReturn(tripViewModelDetail)

        // When
        tripDetailPresenter.subscribe(tripDetailDisplay, 42)
        testScheduler.triggerActions()

        // Then
        inOrder(tripDetailDisplay) {
            then(tripDetailDisplay).should(this).showLoader()
            then(tripDetailDisplay).should(this).displayTripDetail(tripViewModelDetail)
            then(tripDetailDisplay).should(this).hideLoader()
        }
    }

    @Test
    fun `unsubscribe should clear all disposable`() {
        // When
        tripDetailPresenter.unsubscribe()

        // Then
        then(compositeDisposable).should().clear()
    }
}