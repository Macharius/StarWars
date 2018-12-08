package com.remipradal.starwars.triplist

import com.remipradal.starwars.common.RatingViewModelTransformer
import com.remipradal.starwars.common.Trip
import javax.inject.Inject

class TripListDomainToViewModelTransformer @Inject constructor(
    private val ratingViewModelTransformer: RatingViewModelTransformer
) {
    fun transform(tripList: List<Trip>): List<TripViewModel> {
        return tripList.map {
            TripViewModel(
                id = it.id,
                pilotAvatarUrl = it.pilot.avatarUrl,
                pilotName = it.pilot.name.toUpperCase(),
                pilotRatingViewModel = ratingViewModelTransformer.transformToRatingViewModel(it.pilot.rating),
                dropOffPlanetName = it.dropOffPlanet.name,
                pickUpPlanetName = it.pickUpPlanet.name
            )
        }
    }
}