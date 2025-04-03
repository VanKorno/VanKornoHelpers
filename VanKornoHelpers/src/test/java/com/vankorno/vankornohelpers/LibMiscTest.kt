package com.vankorno.vankornohelpers

import junit.framework.TestCase.assertEquals
import org.junit.Test

class LibMiscTest {
    
    @Test
    fun `getTime24 returns correct zero time`() {
        assertEquals("0:00", LibMisc().getTime24(0, 0))
    }
    @Test
    fun `getTime24 returns correct 12+ time`() {
        assertEquals("13:45", LibMisc().getTime24(13, 45))
    }
    
    
    @Test
    fun `toMinSec returns correct zeroes`() {
        assertEquals("0:00", 0.secToMinSec())
    }
    @Test
    fun `toMinSec returns correct full minutes`() {
        assertEquals("2:00", 120.secToMinSec())
    }
    @Test
    fun `toMinSec returns correct partial minutes`() {
        assertEquals("2:03", 123.secToMinSec())
    }
    
    
    
    @Test
    fun `shortenNum returns correct -9999-9999`() {
        assertEquals("-9999", (-9999).shortenNumStr())
        assertEquals("-1000", (-1000).shortenNumStr())
        assertEquals("-1", (-1).shortenNumStr())
        assertEquals("0", 0.shortenNumStr())
        assertEquals("1000", 1000.shortenNumStr())
        assertEquals("8888", 8888.shortenNumStr())
    }
    @Test
    fun `shortenNum returns correct 10k+`() {
        assertEquals("10K", 10340.shortenNumStr())
        assertEquals("100K", 100000.shortenNumStr())
        assertEquals("999K", 999000.shortenNumStr())
    }
    @Test
    fun `shortenNum returns correct 1m+`() {
        assertEquals("2M", 2000340.shortenNumStr())
        assertEquals("200M", 200034000.shortenNumStr())
    }
    @Test
    fun `shortenNum returns correct -10K-`() {
        assertEquals("-10K", (-10340).shortenNumStr())
        assertEquals("-100K", (-100000).shortenNumStr())
        assertEquals("-999K", (-999000).shortenNumStr())
    }
    @Test
    fun `shortenNum returns correct negative M`() {
        assertEquals("-2M", (-2000340).shortenNumStr())
        assertEquals("-200M", (-200034000).shortenNumStr())
    }
    
}