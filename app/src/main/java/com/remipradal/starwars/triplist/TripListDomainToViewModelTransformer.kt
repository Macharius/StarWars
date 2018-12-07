package com.remipradal.starwars.triplist

import com.remipradal.starwars.common.Trip
import javax.inject.Inject

class TripListDomainToViewModelTransformer @Inject constructor() {
    fun transform(tripList: List<Trip>): List<TripViewModel> {
        return tripList.map {
            TripViewModel(
                id = it.id,
                pilotAvatarUrl = it.pilot.avatarUrl,
                pilotName = it.pilot.name.toUpperCase(),
                pilotRating = getRating(it.pilot.rating),
                dropOffPlanetName = it.dropOffPlanet.name,
                pickUpPlanetName = it.pickUpPlanet.name
            )
        }
    }

    private fun getRating(rating: Float?): Rating {
        return rating?.let {
            Rating.StarRating(
                getStarType { rating >= 1 },
                getStarType { rating >= 2 },
                getStarType { rating >= 3 },
                getStarType { rating >= 4 },
                getStarType { rating == 5f }
            )
        } ?: Rating.NoRating
    }

    private fun getStarType(predicate: () -> Boolean) =
        if (predicate()) Rating.StarType.FILLED else Rating.StarType.EMPTY

}