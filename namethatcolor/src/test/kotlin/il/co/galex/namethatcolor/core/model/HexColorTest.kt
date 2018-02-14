package il.co.galex.namethatcolor.core.model

import org.junit.Assert.*
import org.junit.Test

class HexColorTest {

    @Test
    fun `input color of 3 chars`() {
        assertEquals(HexColor("32D").toString(), "#3322DD")
    }

    @Test
    fun `input color of 3 chars with #`() {
        assertEquals(HexColor("#32D").toString(), "#3322DD")
    }
    @Test
    fun `input color of 3 chars with alpha`() {
        assertEquals(HexColor("032D").toString(), "#003322DD")
    }

    @Test
    fun `input color of 3 chars with alpha and #`() {
        assertEquals(HexColor("#A32D").toString(), "#AA3322DD")
    }

    @Test
    fun `input color of 6 chars`() {
        assertEquals(HexColor("32DAE1").toString(), "#32DAE1")
    }

    @Test
    fun `input color of 6 chars with #`() {
        assertEquals(HexColor("#123456").toString(), "#123456")
    }
    @Test
    fun `input color of 6 chars with alpha`() {
        assertEquals(HexColor("A1B1C1D1").toString(), "#A1B1C1D1")
    }

    @Test
    fun `input color of 6 chars with alpha and #`() {
        assertEquals(HexColor("#AA93214F").toString(), "#AA93214F")
    }

    @Test
    fun `alpha of 0 percent`() {
        assertEquals(HexColor("#0931").percentAlpha(), 0)
    }

    @Test
    fun `alpha of 5 percent`() {
        assertEquals(HexColor("#0D93214F").percentAlpha(), 5)
    }

    @Test
    fun `alpha of 25 percent`() {
        assertEquals(HexColor("#4093214F").percentAlpha(), 25)
    }

    @Test
    fun `alpha of 50 percent`() {
        assertEquals(HexColor("#8093214F").percentAlpha(), 50)
    }

    @Test
    fun `alpha of 75 percent`() {
        assertEquals(HexColor("#BF93214F").percentAlpha(), 75)
    }

    @Test
    fun `alpha of 100 percent`() {
        assertEquals(HexColor("#FF93214F").percentAlpha(), 100)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invalid color input of 3 chars`() {
        HexColor("#3ZD")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invalid alpha input of 3 chars`() {
        HexColor("P30D")
    }
}