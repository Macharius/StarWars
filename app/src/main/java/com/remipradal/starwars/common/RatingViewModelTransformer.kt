package com.remipradal.starwars.common

import javax.inject.Inject

class RatingViewModelTransformer @Inject constructor() {

    fun transformToRatingViewModel(rating: Float?): RatingViewModel {
        return rating?.let {
            RatingViewModel.StarRating(
                getStarType { rating >= 1 },
                getStarType { rating >= 2 },
                getStarType { rating >= 3 },
                getStarType { rating >= 4 },
                getStarType { rating == 5f }
            )
        } ?: RatingViewModel.NoRating
    }

    private fun getStarType(predicate: () -> Boolean) =
        if (predicate()) RatingViewModel.StarType.FILLED else RatingViewModel.StarType.EMPTY
}