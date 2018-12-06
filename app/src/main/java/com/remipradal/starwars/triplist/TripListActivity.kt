package com.remipradal.starwars.triplist

import android.os.Bundle
import com.remipradal.starwars.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TripListActivity : DaggerAppCompatActivity(), TripListDisplay {

    @Inject lateinit var controller: TripListController
    @Inject lateinit var displayHolder: DisplayHolder<TripListDisplay>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)
        displayHolder.display = this
        controller.fetchTripList()
    }

    override fun onStop() {
        displayHolder.display = null
        super.onStop()
    }

    override fun displayTripList(tripList: List<TripViewModel>) {
    }
}
