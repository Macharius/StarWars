package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import com.remipradal.starwars.core.triplist.Trip
import com.remipradal.starwars.core.triplist.TripListInteractor
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
class TripListPresenterTest {

    @Mock private lateinit var tripListDisplay: TripListDisplay
    @Mock private lateinit var tripListInteractor: TripListInteractor
    @Mock private lateinit var transformer: TripListDomainToViewModelTransformer
    @Mock private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var tripListPresenter: TripListPresenter

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        tripListPresenter = TripListPresenter(
            tripListInteractor,
            transformer,
            testScheduler,
            testScheduler,
            compositeDisposable
        )
    }

    @Test
    fun `subscribe when an error happens the should display error`() {
        // Given
        given(tripListInteractor.getTripList()).willReturn(Single.error(IOException()))

        // When
        tripListPresenter.subscribe(tripListDisplay)
        testScheduler.triggerActions()

        // Then
        inOrder(tripListDisplay) {
            then(tripListDisplay).should(this).showLoader()
            then(tripListDisplay).should(this).displayError()
            then(tripListDisplay).should(this).hideLoader()
        }
    }

    @Test
    fun `subscribe when a trip list can be retrieved the should displayed it`() {
        // Given
        val tripList = mock<List<Trip>>()
        given(tripListInteractor.getTripList()).willReturn(Single.just(tripList))
        val tripViewModelList = mock<List<TripViewModel>>()
        given(transformer.transform(tripList)).willReturn(tripViewModelList)

        // When
        tripListPresenter.subscribe(tripListDisplay)
        testScheduler.triggerActions()

        // Then
        inOrder(tripListDisplay) {
            then(tripListDisplay).should(this).showLoader()
            then(tripListDisplay).should(this).displayTripList(tripViewModelList)
            then(tripListDisplay).should(this).hideLoader()
        }
    }

    @Test
    fun `unsubscribe should clear all disposable`() {
        // When
        tripListPresenter.unsubscribe()

        // Then
        then(compositeDisposable).should().clear()
    }
}