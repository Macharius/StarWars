package com.remipradal.starwars.tripdetail

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class DropOffPlanetPictureTransformation : BitmapTransformation() {

    companion object {
        private val ID = DropOffPlanetPictureTransformation::class.qualifiedName
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        //  Slightly scale in original bitmap to achieve desired right/bottom crop effect
        val scaleInRatio = 1.10
        val scaledBitmap = Bitmap.createScaledBitmap(
            toTransform,
            (outWidth * scaleInRatio).toInt(),
            (outWidth * scaleInRatio).toInt(),
            false
        )

        return Bitmap.createBitmap(scaledBitmap, 0, 0, outWidth, outHeight)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID?.toByteArray())
    }

    override fun equals(other: Any?): Boolean = other is PickUpPlanetPictureTransformation

    override fun hashCode(): Int = ID.hashCode()
}