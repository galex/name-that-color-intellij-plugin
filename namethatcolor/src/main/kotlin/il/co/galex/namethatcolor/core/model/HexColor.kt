package il.co.galex.namethatcolor.core.model

import il.co.galex.namethatcolor.core.util.hsl
import il.co.galex.namethatcolor.core.util.rgb
import il.co.galex.namethatcolor.core.util.roundTo2Decimal
import il.co.galex.namethatcolor.core.util.roundTo2HexString

class HexColor(val input: String) {

    private var alpha: String? = null
    private var hasPercent = false
    var percentAlpha: Int? = null
    val value: String

    init {
        var cup = input.toUpperCase()

        if (cup.isEmpty()) {
            throw IllegalArgumentException("The input cannot be an empty string")
        }

        if (cup.startsWith("#")) {
            cup = cup.substringAfter("#")
        }

        // Add support for Alpha format of percentage : 10%
        if (cup.contains("%")) {
            hasPercent = true
            val alphaValueText = cup.substringBefore("%").trim()
            if (alphaValueText.isNotEmpty()) {
                val alphaValue = alphaValueText.toInt()
                alpha = (alphaValue / 100.0 * 255).roundTo2HexString().toUpperCase()
                percentAlpha = alphaValue
            }
            cup = cup.substringAfter("%")
        }

        when (cup.length) {
            3 -> {
                this.value = cup[0] + cup[0] + cup[1] + cup[1] + cup[2] + cup[2]
            }

            4 -> {
                if (hasPercent) {
                    throw IllegalArgumentException("Length is weird")
                }
                this.value = cup[1] + cup[1] + cup[2] + cup[2] + cup[3] + cup[3]
                this.alpha = cup[0] + cup[0]
            }

            6 -> {
                this.value = cup
            }

            8 -> {
                if (hasPercent) {
                    throw IllegalArgumentException("Length is weird")
                }
                this.value = cup.substring(2)
                this.alpha = cup.substring(0, 2)
            }

            else -> throw IllegalArgumentException("Length is weird")
        }
        // if we survived till here, let's now check the format of the value itself
        if (!VALUE_REGEX.matches(value)) {
            throw IllegalArgumentException("The value $value is not of a correct format")
        }

        alpha?.let {
            if (!ALPHA_REGEX.matches(it)) {
                throw IllegalArgumentException("The alpha $alpha is not of a correct format")
            }
            if (!hasPercent) {
                percentAlpha = (it.toInt(16) / 255.0).roundTo2Decimal()
            }
        }
    }

    fun rgb() = value.rgb()
    fun hsl() = value.hsl()

    override fun toString(): String = PREFIX + if (alpha == null) value else "$alpha$value"

    companion object {
        private const val PREFIX = "#"
        private val VALUE_REGEX = Regex("[0-9a-fA-F]{6}$")
        private val ALPHA_REGEX = Regex("[0-9a-fA-F]{2}$")
    }

    private operator fun Char.plus(c: Char): String {
        return "$this$c"
    }
}


