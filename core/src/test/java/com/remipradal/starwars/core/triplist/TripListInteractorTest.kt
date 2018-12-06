package com.remipradal.starwars.core.triplist

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripListInteractorTest {

    @InjectMocks private lateinit var tripListInteractor: TripListInteractor
    @Mock private lateinit var tripListPresenter: TripListPresenter
    @Mock private lateinit var tripListRepository: TripListRepository

    @Test
    fun `fetchTripList should show loader before call to repository and dismiss it after`() {
        // When
        tripListInteractor.fetchTripList()

        // then
        inOrder(tripListPresenter, tripListRepository) {
            then(tripListPresenter).should(this).presentLoader(TripListPresenter.LoaderState.DISPLAYED)
            then(tripListRepository).should(this).getTripList()
            then(tripListPresenter).should(this).presentLoader(TripListPresenter.LoaderState.HIDDEN)
        }
    }

    @Test
    fun `fetchTripList when repository returns error then should present error message`() {
        // Given
        given(tripListRepository.getTripList()).willReturn(TripListResponse.Error)

        // When
        tripListInteractor.fetchTripList()

        // then
        then(tripListPresenter).should().presentErrorMessage()
    }

    @Test
    fun `fetchTripList when repository returns list then should present the list`() {
        // Given
        val tripList = mock<List<Trip>>()
        given(tripListRepository.getTripList()).willReturn(TripListResponse.Data(tripList))

        // When
        tripListInteractor.fetchTripList()

        // then
        then(tripListPresenter).should().presentTripList(tripList)
    }
}