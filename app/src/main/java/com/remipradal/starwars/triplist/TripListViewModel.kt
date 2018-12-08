package com.remipradal.starwars.triplist

import com.remipradal.starwars.common.RatingViewModel

data class TripViewModel(
    val id: Int,
    val pilotAvatarUrl: String,
    val pilotName: String,
    val pilotRatingViewModel: RatingViewModel,
    val pickUpPlanetName: String,
    val dropOffPlanetName: String
)