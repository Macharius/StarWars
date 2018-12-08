package com.remipradal.starwars.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RatingViewModelTransformerTest {

    private val ratingViewModelTransformer = RatingViewModelTransformer()

    @Test
    fun `transformToRatingViewModel when rating is null should not show rating`() {
        // When
        val ratingViewModel = ratingViewModelTransformer.transformToRatingViewModel(null)

        // Then
        assertThat(ratingViewModel).isEqualTo(RatingViewModel.NoRating)
    }

    @Test
    fun `transformToRatingViewModel when rating is below 1 should have only empty stars`() {
        // When
        val ratingViewModel = ratingViewModelTransformer.transformToRatingViewModel(0.9f)

        // Then
        assertThat(ratingViewModel).isEqualTo(
            RatingViewModel.StarRating(
                RatingViewModel.StarType.EMPTY,
                RatingViewModel.StarType.EMPTY,
                RatingViewModel.StarType.EMPTY,
                RatingViewModel.StarType.EMPTY,
                RatingViewModel.StarType.EMPTY
            )
        )
    }

    @Test
    fun `transformToRatingViewModel when rating is above 1 but below 2 then should have a rating with only the first star filled`() {
        // When
        val ratingViewModel = ratingViewModelTransformer.transformToRatingViewModel(1.1f)

        // Then
        assertThat(ratingViewModel).isEqualTo(
            RatingViewModel.StarRating(
                RatingViewModel.StarType.FILLED,
                RatingViewModel.StarType.EMPTY,
                RatingViewModel.StarType.EMPTY,
                RatingViewModel.StarType.EMPTY,
                RatingViewModel.StarType.EMPTY
            )
        )
    }

    @Test
    fun `transformToRatingViewModel when rating is 5 then should have all stars filled`() {
        // When
        val ratingViewModel = ratingViewModelTransformer.transformToRatingViewModel(5f)

        // Then
        assertThat(ratingViewModel).isEqualTo(
            RatingViewModel.StarRating(
                RatingViewModel.StarType.FILLED,
                RatingViewModel.StarType.FILLED,
                RatingViewModel.StarType.FILLED,
                RatingViewModel.StarType.FILLED,
                RatingViewModel.StarType.FILLED
            )
        )
    }

}