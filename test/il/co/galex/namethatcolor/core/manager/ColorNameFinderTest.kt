package il.co.galex.namethatcolor.core.manager

import org.junit.Assert.assertEquals
import org.junit.Test

class ColorNameFinderTest {

    @Test
    fun name() {
        // black exact match
        assertEquals("Black", ColorNameFinder.color("#000000").name)
        // non exact match
        assertEquals("Black Russian", ColorNameFinder.color("#000010").name)
        // white exact match
        assertEquals("White", ColorNameFinder.color("#FFFFFF").name)
        // white with 3 letters only
        assertEquals("White", ColorNameFinder.color("#FFF").name)
    }
}
