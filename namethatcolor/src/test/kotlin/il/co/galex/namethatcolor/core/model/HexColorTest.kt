package il.co.galex.namethatcolor.core.model

import il.co.galex.namethatcolor.core.util.toXmlName
import org.junit.Assert.assertEquals
import org.junit.Test

class HexColorTest {

    @Test
    fun `input color of 3 chars`() {
        assertEquals("#3322DD", HexColor("32D").toString())
    }

    @Test
    fun `input color of 3 chars with lowercase letters`() {
        assertEquals("#3322DD", HexColor("32d").toString())
    }

    @Test
    fun `input color of 3 chars with #`() {
        assertEquals("#3322DD", HexColor("#32D").toString())
    }

    @Test
    fun `input color of 3 chars with # and lowercase letters`() {
        assertEquals("#DDDDDD", HexColor("#ddd").toString())
    }

    @Test
    fun `input color of 3 chars with alpha`() {
        assertEquals("#003322DD", HexColor("032D").toString())
    }

    @Test
    fun `input color of 3 chars with alpha and #`() {
        assertEquals("#AA3322DD", HexColor("#A32D").toString())
    }

    @Test
    fun `input color of 6 chars`() {
        assertEquals("#32DAE1", HexColor("32DAE1").toString())
    }

    @Test
    fun `input color of 6 chars with #`() {
        assertEquals("#123456", HexColor("#123456").toString())
    }

    @Test
    fun `input color of 6 chars with alpha`() {
        assertEquals("#A1B1C1D1", HexColor("A1B1C1D1").toString())
    }

    @Test
    fun `input color of 6 chars with alpha and #`() {
        assertEquals("#AA93214F", HexColor("#AA93214F").toString())
    }

    @Test
    fun `alpha of 0 percent`() {
        assertEquals(0, HexColor("#0931").percentAlpha)
    }

    @Test
    fun `alpha of 0%`() {
        assertEquals(0, HexColor("#0%931").percentAlpha)
        assertEquals("#00993311", HexColor("#0%931").toString())
    }

    @Test
    fun `alpha of 5 percent`() {
        assertEquals(5, HexColor("#0D93214F").percentAlpha)
    }

    @Test
    fun `alpha of 5%`() {
        assertEquals(5, HexColor("#5%93214F").percentAlpha)
        assertEquals("#0D93214F", HexColor("#5%93214F").toString())
    }

    @Test
    fun `alpha of 25 percent`() {
        assertEquals(25, HexColor("#4093214F").percentAlpha)
    }

    @Test
    fun `alpha of 25%`() {
        assertEquals(25, HexColor("#25%93214F").percentAlpha)
        assertEquals("#4093214F", HexColor("#25%93214F").toString())
    }

    @Test
    fun `alpha of 50 percent`() {
        assertEquals(50, HexColor("#8093214F").percentAlpha)
    }

    @Test
    fun `alpha of 50%`() {
        assertEquals(50, HexColor("#50%93214F").percentAlpha)
        assertEquals("#8093214F", HexColor("#50%93214F").toString())
    }

    @Test
    fun `alpha of 75 percent`() {
        assertEquals(75, HexColor("#BF93214F").percentAlpha)
    }

    @Test
    fun `alpha of 75%`() {
        assertEquals(75, HexColor("#75%93214F").percentAlpha)
        assertEquals("#BF93214F", HexColor("#75%93214F").toString())
    }

    @Test
    fun `alpha of 100 percent`() {
        assertEquals(100, HexColor("#FF93214F").percentAlpha)
    }

    @Test
    fun `alpha of 100%`() {
        assertEquals(100, HexColor("#100%93214F").percentAlpha)
        assertEquals("#FF93214F", HexColor("#100%93214F").toString())
    }

    @Test
    fun `ignore default alpha value`() {
        assertEquals("black", "black".toXmlName(HexColor("000000").percentAlpha))
        assertEquals("black", "black".toXmlName(HexColor("%000000").percentAlpha))
        assertEquals("black_alpha_100", "black".toXmlName(HexColor("100%000000").percentAlpha))
        assertEquals("black_alpha_0", "black".toXmlName(HexColor("0%000000").percentAlpha))
        assertEquals("black_alpha_10", "black".toXmlName(HexColor("10%000000").percentAlpha))
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