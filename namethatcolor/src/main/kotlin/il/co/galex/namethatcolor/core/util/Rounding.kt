package il.co.galex.namethatcolor.core.util

fun Double.round() = Math.round(this).toInt()
fun Double.roundTo2Decimal() = Math.round(this * 100.0).toInt()