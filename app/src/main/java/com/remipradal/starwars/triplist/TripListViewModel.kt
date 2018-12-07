package com.remipradal.starwars.triplist

data class TripViewModel(
    val id: Int,
    val pilotAvatarUrl: String,
    val pilotName: String,
    val pilotRating: Rating,
    val pickUpPlanetName: String,
    val dropOffPlanetName: String
)

sealed class Rating {

    object NoRating : Rating()
    data class StarRating(
        val firstStar: StarType,
        val secondStar: StarType,
        val thirdStar: StarType,
        val fourthStar: StarType,
        val fifthStar: StarType
    ) : Rating()

    enum class StarType { FILLED, EMPTY }
}