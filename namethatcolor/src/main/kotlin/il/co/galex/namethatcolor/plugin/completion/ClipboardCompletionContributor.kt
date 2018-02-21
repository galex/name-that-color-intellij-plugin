package il.co.galex.namethatcolor.plugin.completion

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
import il.co.galex.namethatcolor.plugin.util.NAME_THAT_COLOR
import il.co.galex.namethatcolor.plugin.util.NAME_THAT_MATERIAL_COLOR
import il.co.galex.namethatcolor.plugin.util.PLACE
import il.co.galex.namethatcolor.plugin.util.addElement

/**
 *  Completes the color from the clipboard
 */
class ClipboardCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC, PLACE, object : CompletionProvider<CompletionParameters>() {

            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?, resultSet: CompletionResultSet) {

                ClipboardUtil.getTextInClipboard()?.let {
                    resultSet.addElement(NAME_THAT_COLOR, it, ColorNameFinder::findColor)
                    resultSet.addElement(NAME_THAT_MATERIAL_COLOR, it, ColorNameFinder::findMaterialColor)
                }
            }
        })
    }
}
