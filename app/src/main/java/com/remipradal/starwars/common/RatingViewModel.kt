package com.remipradal.starwars.common

sealed class RatingViewModel {

    object NoRating : RatingViewModel()
    data class StarRating(
        val firstStar: StarType,
        val secondStar: StarType,
        val thirdStar: StarType,
        val fourthStar: StarType,
        val fifthStar: StarType
    ) : RatingViewModel()

    enum class StarType { FILLED, EMPTY }
}