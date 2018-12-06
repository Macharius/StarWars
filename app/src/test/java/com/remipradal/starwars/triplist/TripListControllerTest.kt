package com.remipradal.starwars.triplist

import com.nhaarman.mockitokotlin2.then
import com.remipradal.starwars.core.triplist.TripListInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TripListControllerTest {

    @Mock private lateinit var tripListInteractor: TripListInteractor
    private lateinit var tripListController: TripListController


    @Before
    fun setUp() {
        tripListController = TripListController(tripListInteractor, CoroutineScope(Dispatchers.Default))
    }

    @Test
    fun `fetchTripList should call interactor`() {
        // When
        tripListController.fetchTripList()

        // Then
        then(tripListInteractor).should().fetchTripList()
    }
}