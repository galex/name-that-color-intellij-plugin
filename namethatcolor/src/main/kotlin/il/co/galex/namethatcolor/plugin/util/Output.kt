package il.co.galex.namethatcolor.plugin.util

private val nonWordCharRegEx by lazy {
    "\\W+".toRegex()
}

fun xmlOutput(name: String, hexColor: String) = "<color name=\"$name\">$hexColor</color>"
fun kotlinOutput(name: String, hexColor: String): String {
    return "val $name = Color(0xff${
        hexColor.replace(
            nonWordCharRegEx, ""
        )
    })"
}