package il.co.galex.namethatcolor.plugin

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.completion.CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.application.ex.ClipboardUtil
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.intellij.util.ProcessingContext
import il.co.galex.namethatcolor.core.exception.ColorNotFoundException
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.model.Color
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.core.util.toXmlName

class ClipboardCompletionContributor : CompletionContributor() {

    companion object {
        private const val RESOURCES_TAG_NAME = "resources"
        private val place = XmlPatterns.psiElement(XmlToken::class.java)
                .withText(DUMMY_IDENTIFIER_TRIMMED)
                .withParent(XmlPatterns.xmlText()
                        .withParent(XmlPatterns.psiElement(XmlTag::class.java)
                                .withName(RESOURCES_TAG_NAME)
                        )
                )
    }

    init {
        extend(CompletionType.BASIC, place, object : CompletionProvider<CompletionParameters>() {

            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?, resultSet: CompletionResultSet) {

                ClipboardUtil.getTextInClipboard()?.let {
                    resultSet.addElement(it, ColorNameFinder::findColor)
                    resultSet.addElement(it, ColorNameFinder::findMaterialColor)
                }
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

            val insert = "<color name=\"$name\">${hexColor.inputToString()}</color>"
            addElement(LookupElementBuilder.create(insert))
        } catch (e: ColorNotFoundException) {
            println(e.localizedMessage)
        } catch (e: IllegalArgumentException) {
            println(e.localizedMessage)
        }
    }
}
