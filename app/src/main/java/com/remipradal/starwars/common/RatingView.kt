package com.remipradal.starwars.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import com.remipradal.starwars.R
import kotlinx.android.synthetic.main.view_pilot_rating.view.*

class RatingView : ConstraintLayout {

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_pilot_rating, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.RatingView, 0, 0)
            val filledStarDrawableResId =
                typedArray.getResourceId(R.styleable.RatingView_filledStarDrawable, R.drawable.ic_star_filled_32dp)
            val emptyStarDrawableResourceId =
                typedArray.getResourceId(R.styleable.RatingView_emptyStarDrawable, R.drawable.ic_star_empty_32dp)
            typedArray.recycle()
            filledStarDrawable = ContextCompat.getDrawable(context, filledStarDrawableResId)
            emptyStarDrawable = ContextCompat.getDrawable(context, emptyStarDrawableResourceId)
        }
    }

    private var filledStarDrawable: Drawable? = null
    private var emptyStarDrawable: Drawable? = null

    fun bindRatingViewModel(starRating: RatingViewModel.StarRating) {
        mapOf(
            firstStarImageView to starRating.firstStar,
            secondStarImageView to starRating.secondStar,
            thirdStarImageView to starRating.thirdStar,
            fourthStarImageView to starRating.fourthStar,
            fifthStarImageView to starRating.fifthStar
        ).forEach { applyStarRating(it.key, it.value) }
    }

    private fun applyStarRating(starImageView: ImageView, starType: RatingViewModel.StarType) {
        val starDrawable = when (starType) {
            RatingViewModel.StarType.FILLED -> filledStarDrawable
            RatingViewModel.StarType.EMPTY -> emptyStarDrawable
        }
        starImageView.setImageDrawable(starDrawable)
    }

}
