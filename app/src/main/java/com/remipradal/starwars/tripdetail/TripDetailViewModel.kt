package com.remipradal.starwars.tripdetail

data class TripDetailViewModel(
    val pilotName: String,
    val pilotAvatarUrl: String,
    val pickUpPlanetName: String,
    val pickUpPassageHour: String,
    val pickUpPlanetImageUrl: String,
    val dropOffPlanetName: String,
    val dropOffPassageHour: String,
    val dropOffPlanetImageUrl: String,
    val tripDistance: String,
    val tripDuration: String
)