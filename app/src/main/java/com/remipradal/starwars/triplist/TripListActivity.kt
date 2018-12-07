package com.remipradal.starwars.triplist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.remipradal.starwars.R
import com.remipradal.starwars.tripdetail.TripDetailActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_trip_list.*
import javax.inject.Inject

class TripListActivity : DaggerAppCompatActivity(), TripListDisplay {

    @Inject
    lateinit var tripListPresenter: TripListPresenter

    private val tripListAdapter = TripListAdapter {
        startActivity(TripDetailActivity.getLaunchIntent(this, it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)

        tripRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TripListActivity)
            adapter = tripListAdapter
            val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(this@TripListActivity, R.drawable.bg_separator)
                ?.let { dividerItemDecoration.setDrawable(it) }
            addItemDecoration(dividerItemDecoration)
        }
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
        tripListAdapter.tripViewModelList = tripList
        tripListAdapter.notifyDataSetChanged()
    }

    override fun showLoader() {
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
