package il.co.galex.namethatcolor.plugin.completion

import com.intellij.codeInsight.completion.*
import com.intellij.openapi.application.ex.ClipboardUtil
import com.intellij.util.ProcessingContext
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
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

            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {

                ClipboardUtil.getTextInClipboard()?.let {
                    resultSet.addElement(NAME_THAT_COLOR, it, ColorNameFinder::findColor)
                    resultSet.addElement(NAME_THAT_MATERIAL_COLOR, it, ColorNameFinder::findMaterialColor)
                }
            }
        })
    }
}
