package com.remipradal.starwars.tripdetail

import com.remipradal.starwars.common.RatingViewModel

data class TripDetailViewModel(
    val pilotName: String,
    val pilotAvatarUrl: String,
    val pilotRatingViewModel: RatingViewModel,
    val pickUpPlanetName: String,
    val pickUpPassageHour: String,
    val pickUpPlanetImageUrl: String,
    val dropOffPlanetName: String,
    val dropOffPassageHour: String,
    val dropOffPlanetImageUrl: String,
    val tripDistance: String,
    val tripDuration: String
)