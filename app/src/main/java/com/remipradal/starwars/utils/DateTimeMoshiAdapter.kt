package com.remipradal.starwars.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.DateTime
import javax.inject.Inject

class DateTimeMoshiAdapter @Inject constructor() {
    @Suppress("unused")
    @ToJson
    fun toJson(dateTime: DateTime): String {
        return dateTime.toString()
    }

    @Suppress("unused")
    @FromJson
    fun fromJson(dateTime: String): DateTime {
        return DateTime.parse(dateTime)
    }
}