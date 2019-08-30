package com.neandril.mynews

import com.neandril.mynews.utils.paddingZero
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {

    /**
     * Test if months and days are 2 digits
     */
    @Test
    fun paddingZero_test() {
        val yy = "2019"
        val mm = "9".toInt().paddingZero()
        val dd = "2".toInt().paddingZero()
        val date = "$yy$mm$dd"

        assertEquals("20190902", date)
    }
}