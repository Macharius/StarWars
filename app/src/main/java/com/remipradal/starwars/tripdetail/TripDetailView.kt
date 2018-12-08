package com.remipradal.starwars.tripdetail

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.remipradal.starwars.R
import com.remipradal.starwars.common.RatingViewModel
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
            loadImages()

            pilotNameTextView.text = pilotName
            pickUpPlanetNameTextView.text = pickUpPlanetName
            pickUpTimeTextView.text = pickUpPassageHour

            dropOffPlanetNameTextView.text = dropOffPlanetName
            dropOffTimeTextView.text = dropOffPassageHour

            tripDurationTextView.text = tripDuration
            tripDistanceTextView.text = tripDistance

            displayPilotRating(pilotRatingViewModel)
        }
    }

    private fun displayPilotRating(pilotRatingViewModel: RatingViewModel) = when (pilotRatingViewModel) {
        RatingViewModel.NoRating -> {
            noRatingTextView.visibility = View.VISIBLE
            ratingView.visibility = View.GONE
        }
        is RatingViewModel.StarRating -> {
            noRatingTextView.visibility = View.GONE
            ratingView.visibility = View.VISIBLE
            ratingView.bindRatingViewModel(pilotRatingViewModel)
        }
    }


    private fun TripDetailViewModel.loadImages() {
        Glide.with(this@TripDetailView).run {
            load(pilotAvatarUrl).transition(withCrossFade()).into(pilotAvatarImageView)
            load(pickUpPlanetImageUrl)
                .transition(withCrossFade())
                .apply(
                    RequestOptions()
                        .downsample(DownsampleStrategy.NONE)
                        .transform(PickUpPlanetPictureTransformation())
                )
                .into(pickUpPlanetImageView)

            load(dropOffPlanetImageUrl)
                .transition(withCrossFade())
                .apply(
                    RequestOptions()
                        .downsample(DownsampleStrategy.NONE)
                        .transform(DropOffPlanetPictureTransformation())
                )
                .into(dropOffPlanetImageView)
        }
    }

}

