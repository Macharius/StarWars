package com.remipradal.starwars.triplist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            //dropOffTextView.text = tripViewModel.dropOffPlanetName

            when (tripViewModel.pilotRatingViewModel) {
                is RatingViewModel.NoRating -> ratingView.visibility = View.GONE
                is RatingViewModel.StarRating -> {
                    ratingView.visibility = View.VISIBLE
                    ratingView.bindRatingViewModel(tripViewModel.pilotRatingViewModel)
                }
            }

            hitBoxView.setOnClickListener { onClickCallback(tripViewModel.id) }
        }
    }

}