package il.co.galex.namethatcolor.plugin

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.application.ex.ClipboardUtil
import com.intellij.patterns.XmlPatterns
import com.intellij.psi.impl.source.xml.XmlTagImpl
import com.intellij.util.ProcessingContext
import il.co.galex.namethatcolor.core.exception.ColorNotFoundException
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.util.isValid
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
                        if (textInClipboard.isNotEmpty() && textInClipboard.isValid()) {

                            try {
                                val color = ColorNameFinder.color(textInClipboard)

                                val insertedColor = if (!textInClipboard.startsWith("#")) "#$textInClipboard" else textInClipboard

                                val insert = "<color name=\"${color.name.toXmlName()}\">$insertedColor</color>"
                                //val message = "Add $insertedColor as color resource named \"${color.name}\""
                                resultSet.addElement(LookupElementBuilder.create(insert))

                            } catch (e: ColorNotFoundException) {
                            }
                        }
                    }
                }
            }
        })
    }
}