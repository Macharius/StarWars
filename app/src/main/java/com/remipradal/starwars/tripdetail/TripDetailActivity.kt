package com.remipradal.starwars.tripdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import com.remipradal.starwars.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_trip_detail.*
import javax.inject.Inject

class TripDetailActivity : DaggerAppCompatActivity(), TripDetailDisplay {

    companion object {
        private const val TRIP_ID_EXTRA = "TRIP_ID_EXTRA"

        fun getLaunchIntent(context: Context, tripId: Int): Intent {
            return Intent(context, TripDetailActivity::class.java).apply {
                putExtra(TRIP_ID_EXTRA, tripId)
            }
        }
    }

    @Inject
    lateinit var tripDetailPresenter: TripDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tripDetailPresenter.subscribe(this, intent.getIntExtra(TRIP_ID_EXTRA, 0))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        tripDetailPresenter.unsubscribe()
        super.onDestroy()
    }

    override fun displayTripDetail(tripDetailViewModel: TripDetailViewModel) {
        tripDetailView.bindData(tripDetailViewModel)
        tripDetailView.visibility = View.VISIBLE
    }

    override fun showLoader() {
        tripDetailView.visibility = View.GONE
        loader.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader.visibility = View.GONE
    }

    override fun displayError() {
        Snackbar.make(findViewById(android.R.id.content), R.string.trip_list_error_message, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}