package com.neandril.mynews

import com.neandril.mynews.utils.paddingZero
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Theses test verify the models
 */

class ExtensionTest {

    @Test
    fun date_shouldFormattedCorrectly() {

        val year = 2019
        val month = 7
        val day = 2
        val date = "$year${month.paddingZero()}${day.paddingZero()}"

        assertEquals("20190702", date)
    }
}