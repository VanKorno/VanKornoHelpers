package com.vankorno.vankornohelpers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LibMiscTest {
    
    @Test
    fun `getTime24 returns correct zero time`() {
        assertEquals("0:00", LibMisc().getTime24(0, 0), "Time mismatch for 0 hours and 0 minutes")
    }
    @Test
    fun `getTime24 returns correct 12+ time`() {
        assertEquals("13:45", LibMisc().getTime24(13, 45), "Time mismatch for 13 hours and 45 minutes")
    }
    
    
    @Test
    fun `toMinSec returns correct zeroes`() {
        assertEquals("0:00", 0.secToMinSec(), "Conversion of 0 seconds to min:sec mismatch")
    }
    @Test
    fun `toMinSec returns correct full minutes`() {
        assertEquals("2:00", 120.secToMinSec(), "Conversion of 120 seconds to min:sec mismatch")
    }
    @Test
    fun `toMinSec returns correct partial minutes`() {
        assertEquals("2:03", 123.secToMinSec(), "Conversion of 123 seconds to min:sec mismatch")
    }
    
    
    
    @Test
    fun `shortenNum returns correct -9999-9999`() {
        assertEquals("-9999", (-9999).shortenNumStr(), "Number mismatch for -9999")
        assertEquals("-1000", (-1000).shortenNumStr(), "Number mismatch for -1000")
        assertEquals("-1", (-1).shortenNumStr(), "Number mismatch for -1")
        assertEquals("0", 0.shortenNumStr(), "Number mismatch for 0")
        assertEquals("1000", 1000.shortenNumStr(), "Number mismatch for 1000")
        assertEquals("8888", 8888.shortenNumStr(), "Number mismatch for 8888")
    }
    @Test
    fun `shortenNum returns correct 10k+`() {
        assertEquals("10K", 10340.shortenNumStr(), "Number mismatch for 10340")
        assertEquals("100K", 100000.shortenNumStr(), "Number mismatch for 100000")
        assertEquals("999K", 999000.shortenNumStr(), "Number mismatch for 999000")
    }
    @Test
    fun `shortenNum returns correct 1m+`() {
        assertEquals("2M", 2000340.shortenNumStr(), "Number mismatch for 2000340")
        assertEquals("200M", 200034000.shortenNumStr(), "Number mismatch for 200034000")
    }
    @Test
    fun `shortenNum returns correct -10K-`() {
        assertEquals("-10K", (-10340).shortenNumStr(), "Number mismatch for -10340")
        assertEquals("-100K", (-100000).shortenNumStr(), "Number mismatch for -100000")
        assertEquals("-999K", (-999000).shortenNumStr(), "Number mismatch for -999000")
    }
    @Test
    fun `shortenNum returns correct negative M`() {
        assertEquals("-2M", (-2000340).shortenNumStr(), "Number mismatch for -2000340")
        assertEquals("-200M", (-200034000).shortenNumStr(), "Number mismatch for -200034000")
    }
    
}