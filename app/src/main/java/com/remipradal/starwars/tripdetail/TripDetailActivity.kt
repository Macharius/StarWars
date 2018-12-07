package com.remipradal.starwars.tripdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.remipradal.starwars.R
import dagger.android.support.DaggerAppCompatActivity

class TripDetailActivity : DaggerAppCompatActivity() {

    companion object {
        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, TripDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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
}