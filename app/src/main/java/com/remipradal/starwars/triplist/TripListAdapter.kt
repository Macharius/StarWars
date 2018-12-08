package com.remipradal.starwars.triplist

import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.remipradal.starwars.R
import com.remipradal.starwars.common.RatingViewModel
import kotlinx.android.synthetic.main.cell_trip.view.*

class TripListAdapter(private val onClickCallback: ((TripId) -> Unit)) : RecyclerView.Adapter<TripViewHolder>() {

    var tripViewModelList: List<TripViewModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_trip, parent, false)
        return TripViewHolder(view, onClickCallback)
    }

    override fun getItemCount(): Int = tripViewModelList.size

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bindViewModel(tripViewModelList[position])
    }

}

typealias TripId = Int

class TripViewHolder(
    view: View,
    private val onClickCallback: ((TripId) -> Unit)
) : RecyclerView.ViewHolder(view) {
    fun bindViewModel(tripViewModel: TripViewModel) {
        with(itemView) {
            Glide.with(this).load(tripViewModel.pilotAvatarUrl).into(pilotAvatarImageView)
            pilotNameTextView.text = tripViewModel.pilotName
            pickUpPlanetTextView.text = tripViewModel.pickUpPlanetName
            dropOffTextView.text = tripViewModel.dropOffPlanetName

            when (tripViewModel.pilotRatingViewModel) {
                is RatingViewModel.NoRating -> starGroup.visibility = View.GONE
                is RatingViewModel.StarRating -> {
                    starGroup.visibility = View.VISIBLE
                    bindRating(tripViewModel.pilotRatingViewModel)
                }
            }

            setOnClickListener { onClickCallback(tripViewModel.id) }
        }
    }

    private fun bindRating(rating: RatingViewModel) = with(itemView) {
        when (rating) {
            is RatingViewModel.NoRating -> starGroup.visibility = View.GONE
            is RatingViewModel.StarRating -> {
                starGroup.visibility = View.VISIBLE
                mapOf(
                    firstStarImageView to rating.firstStar,
                    secondStarImageView to rating.secondStar,
                    thirdStarImageView to rating.thirdStar,
                    fourthStarImageView to rating.fourthStar,
                    fifthStarImageView to rating.fifthStar
                ).forEach { applyStarRating(it.key, it.value) }
            }
        }
    }

    private fun applyStarRating(starImageView: ImageView, starType: RatingViewModel.StarType) {
        val starDrawable = getStarDrawable(starType)
        starImageView.setImageDrawable(ContextCompat.getDrawable(starImageView.context, starDrawable))
    }

    @DrawableRes
    private fun getStarDrawable(starType: RatingViewModel.StarType) = when (starType) {
        RatingViewModel.StarType.FILLED -> R.drawable.ic_star_filled
        RatingViewModel.StarType.EMPTY -> R.drawable.ic_star_empty
    }

}