package il.co.galex.namethatcolor.core.util

import il.co.galex.namethatcolor.core.model.Hsl
import il.co.galex.namethatcolor.core.model.Rgb
import il.co.galex.namethatcolor.plugin.util.KOTLIN_ALPHA_SEPARATOR
import il.co.galex.namethatcolor.plugin.util.XML_ALPHA_SEPARATOR
import kotlin.math.roundToInt

/**
 * Transforms a hexadecimal color like "8D90A1" to an Rgb(141, 144, 161)
 */
fun String.rgb() = Rgb(
    r = this.substring(0, 2).toInt(16),
    g = this.substring(2, 4).toInt(16),
    b = this.substring(4, 6).toInt(16)
)

/**
 * Transforms an hexadecimal color like "#8D90A1" to an Hsl(231, 10, 59)
 * Based on http://www.niwa.nu/2013/05/math-behind-colorspace-conversions-rgb-hsl/
 */
fun String.hsl(): Hsl {

    val (r, g, b) = this.rgb().percent()

    val min = minOf(r, b, g)
    val max = maxOf(r, b, g)

    val l = (min + max) / 2

    if (min == max) {
        return Hsl(0, 0, l.roundTo2Decimal())
    } else {

        //If Luminance is smaller then 0.5, then Saturation = (max-min)/(max+min)
        //If Luminance is bigger then 0.5. then Saturation = ( max-min)/(2.0-max-min)
        val s = if (l < 0.5) (max - min) / (max + min) else (max - min) / (2.0 - max - min)

        //If Red is max, then Hue = (G-B)/(max-min)
        //If Green is max, then Hue = 2.0 + (B-R)/(max-min)
        //If Blue is max, then Hue = 4.0 + (R-G)/(max-min)
        var h = when (max) {
            r -> (g - b) / (max - min)
            g -> 2.0 + (b - r) / (max - min)
            b -> 4.0 + (r - g) / (max - min)
            else -> throw IllegalStateException("no way")
        }

        h *= 60
        if (h < 0) h += 360

        return Hsl(h.roundToInt(), s.roundTo2Decimal(), l.roundTo2Decimal())
    }
}


fun String.toXmlName(percentAlpha: Int?): String {
    var name = this.toLowerCase().replace(" ", "_")
    if (percentAlpha != null) {
        name += "$XML_ALPHA_SEPARATOR$percentAlpha"
    }
    return name
}

fun String.toKotlinName(percentAlpha: Int?): String {
    var name = this.replace(" ", "")
    if (percentAlpha != null) {
        name += "$KOTLIN_ALPHA_SEPARATOR$percentAlpha"
    }
    return name
}