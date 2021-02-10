package il.co.galex.namethatcolor.plugin.completion

import com.intellij.codeInsight.completion.*
import com.intellij.openapi.application.ex.ClipboardUtil
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.plugin.util.NAME_THAT_COLOR
import il.co.galex.namethatcolor.plugin.util.NAME_THAT_MATERIAL_COLOR
import il.co.galex.namethatcolor.plugin.util.addKotlinElement

/**
 *  Completes the color from the clipboard
 */
class ClipboardCompletionKotlinContributor : CompletionContributor() {

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {

                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    resultSet: CompletionResultSet
                ) {

                    ClipboardUtil.getTextInClipboard()?.let {
                        resultSet.addKotlinElement(NAME_THAT_COLOR, it, ColorNameFinder::findColor)
                        resultSet.addKotlinElement(NAME_THAT_MATERIAL_COLOR, it, ColorNameFinder::findMaterialColor)
                    }
                }
            })
    }
}
