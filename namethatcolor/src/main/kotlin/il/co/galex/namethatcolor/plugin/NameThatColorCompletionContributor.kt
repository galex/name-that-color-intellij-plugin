package il.co.galex.namethatcolor.plugin

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.application.ex.ClipboardUtil
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.impl.source.xml.XmlTagImpl
import com.intellij.util.ProcessingContext
import il.co.galex.namethatcolor.core.exception.ColorNotFoundException
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.model.Color
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.core.util.toXmlName

class NameThatColorCompletionContributor : CompletionContributor() {

    init {

        val place = XmlPatterns.psiElement()

        extend(CompletionType.BASIC, place, object : CompletionProvider<CompletionParameters>() {

            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?, resultSet: CompletionResultSet) {

                val xmlTagImpl = parameters.position.parent.parent as? XmlTagImpl
                if (xmlTagImpl?.name == "resources") {

                    val textInClipboard = ClipboardUtil.getTextInClipboard()
                    textInClipboard?.let {

                        resultSet.addElement(textInClipboard, ColorNameFinder::findColor)
                        resultSet.addElement(textInClipboard, ColorNameFinder::findMaterialColor)
                    }
                }
            }
        })
    }

    private inline fun CompletionResultSet.addElement(textInClipboard: String, find: (color: HexColor) -> Pair<HexColor, Color>) {

        try {
            val colors = find(HexColor(textInClipboard))

            var name = colors.second.name.toXmlName()

            val percentAlpha = colors.first.percentAlpha()
            if(percentAlpha != null) {
                name += "_$percentAlpha"
            }

            val insert = "<color name=\"$name\">${colors.first.toString()}</color>"
            addElement(LookupElementBuilder.create(insert))
        } catch (e: ColorNotFoundException) {
            println(e.localizedMessage)
        } catch (e: IllegalArgumentException) {
            println(e.localizedMessage)
        }
    }
}

