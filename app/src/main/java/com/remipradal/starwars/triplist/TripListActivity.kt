package com.remipradal.starwars.triplist

import android.os.Bundle
import android.widget.Toast
import com.remipradal.starwars.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TripListActivity : DaggerAppCompatActivity(), TripListDisplay {

    @Inject lateinit var tripListPresenter: TripListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)
    }

    override fun onResume() {
        super.onResume()
        tripListPresenter.subscribe(this)
    }

    override fun onPause() {
        tripListPresenter.unsubscribe()
        super.onPause()
    }

    override fun displayTripList(tripList: List<TripViewModel>) {
        Toast.makeText(this, "trip number ${tripList.size}", Toast.LENGTH_LONG).show()
    }

    override fun showLoader() {
    }

    override fun hideLoader() {
    }

    override fun displayError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

}
