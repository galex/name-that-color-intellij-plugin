package il.co.galex.namethatcolor.plugin.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.completion.CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.intellij.util.ProcessingContext
import il.co.galex.namethatcolor.core.exception.ColorNotFoundException
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.model.Color
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.core.util.toXmlName
import il.co.galex.namethatcolor.plugin.util.PLACE

/**
 * Completes the color on the caret after the color was written
 * (For some reason this shows up in the IDE only for lowercase entered colors)
 */
class CaretCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC, PLACE, object : CompletionProvider<CompletionParameters>() {

            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?, resultSet: CompletionResultSet) {

                val text = parameters.position.text.replace(DUMMY_IDENTIFIER_TRIMMED, "")

                resultSet.addElement(text, ColorNameFinder::findColor)
                resultSet.addElement(text, ColorNameFinder::findMaterialColor)
            }
        })
    }

    private inline fun CompletionResultSet.addElement(clipboard: String, find: (color: HexColor) -> Pair<HexColor, Color>) {

        try {
            val (hexColor, color) = find(HexColor(clipboard))
            var name = color.name.toXmlName()
            val percentAlpha = hexColor.percentAlpha()
            if (percentAlpha != null) {
                name += "_$percentAlpha"
            }

            val insert = "<color name=\"$name\">${hexColor.inputToString().toUpperCase()}</color>"
            addElement(LookupElementBuilder.create(insert))

        } catch (e: ColorNotFoundException) {
            println(e.localizedMessage)
        } catch (e: IllegalArgumentException) {
            println(e.localizedMessage)
        }
    }
}
