package com.remipradal.starwars.tripdetail

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.Duration
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class TimeFormatterTest(
    private val duration: Duration,
    private val expectedPrintedDuration: String
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: duration({0})={1}")
        fun errorCode() = listOf(
            arrayOf(Duration.standardSeconds(5), "0:00:05"),
            arrayOf(Duration.standardMinutes(5), "0:05:00"),
            arrayOf(Duration.standardHours(5), "5:00:00"),
            arrayOf(Duration.standardHours(100), "100:00:00"),
            arrayOf(
                Duration.standardHours(4)
                    .plus(Duration.standardMinutes(12))
                    .plus(Duration.standardSeconds(24)), "4:12:24"
            )
        )
    }

    private val timeFormatter = TimeFormatter()

    @Test
    fun transformToString() {
        // When
        val printedPeriod = timeFormatter.transformToString(duration)

        // Then
        assertThat(printedPeriod).isEqualTo(expectedPrintedDuration)
    }

}