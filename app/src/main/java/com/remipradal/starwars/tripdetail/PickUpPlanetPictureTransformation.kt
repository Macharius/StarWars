package com.remipradal.starwars.tripdetail

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class PickUpPlanetPictureTransformation : BitmapTransformation() {

    companion object {
        private val ID = PickUpPlanetPictureTransformation::class.qualifiedName
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val scaledBitmap = Bitmap.createScaledBitmap(toTransform, outHeight, outHeight, false)
        return Bitmap.createBitmap(scaledBitmap, scaledBitmap.width - outWidth, 0, outWidth, outHeight)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID?.toByteArray())
    }

    override fun equals(other: Any?): Boolean = other is PickUpPlanetPictureTransformation

    override fun hashCode(): Int = ID.hashCode()
}