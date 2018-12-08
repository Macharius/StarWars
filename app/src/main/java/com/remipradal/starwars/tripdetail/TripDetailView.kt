package com.remipradal.starwars.tripdetail

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.remipradal.starwars.R
import kotlinx.android.synthetic.main.view_trip_detail.view.*

class TripDetailView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_trip_detail, this, true)
    }

    fun bindData(viewModel: TripDetailViewModel) {
        with(viewModel) {
            Glide.with(this@TripDetailView).load(pilotAvatarUrl).into(pilotAvatarImageView)

            pilotNameTextView.text = pilotName
            pickUpPlanetNameTextView.text = pickUpPlanetName
            pickUpTimeTextView.text = pickUpPassageHour

            dropOffPlanetNameTextView.text = dropOffPlanetName
            dropOffTimeTextView.text = dropOffPassageHour

            tripDurationTextView.text = tripDuration
            tripDistanceTextView.text = tripDistance
        }
    }
}

