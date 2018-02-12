package il.co.galex.namethatcolor.core.model

data class Rgb(val r: Int, val g: Int, val b: Int) {

    fun percent(): Triple<Double, Double, Double> = Triple(r / 255.0, g / 255.0, b / 255.0)
}