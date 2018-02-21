package il.co.galex.namethatcolor.plugin.intention

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.lang.ASTFactory
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.xml.XmlElementType
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlText
import il.co.galex.namethatcolor.core.model.Color
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.core.util.toXmlName

class NameColorIntention(private val text: String, private val hexColor: HexColor, private val find: (color: HexColor) -> Pair<HexColor, Color>) : IntentionAction {

    override fun getFamilyName(): String = "Name That Color"
    override fun getText(): String = text
    override fun startInWriteAction(): Boolean = true
    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean = true

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {

        if (file is XmlFile) {
            file.rootTag?.let { rootTag ->
                val elements = rootTag.children.filter { it is XmlText }
                elements.forEach { oldElement ->
                    val text = oldElement.text.replace("\n", "").trim()
                    if (oldElement.text.contains(hexColor.input)) {

                        val (hexColor, color) = find(HexColor(hexColor.input))
                        val name = color.name.toXmlName(hexColor.percentAlpha())

                        val insert = "<color name=\"$name\">${hexColor.inputToString()}</color>"
                        var newElement: PsiElement = XmlElementFactory.getInstance(project).createTagFromText(insert)
                        val split = oldElement.text.split(hexColor.input)
                        newElement = oldElement.replace(newElement)
                        // keep what was before and after the entered color
                        if (split.isNotEmpty()) rootTag.addBefore(insert(project, split[0]), newElement)
                        if (split.size > 1) rootTag.addAfter(insert(project, split[1]), newElement)

                        // get out of our loop if we found one to replace
                        return@forEach
                    }
                }
            }
        }
    }

    private fun insert(project: Project, text: String): XmlText {
        val tagFromText = XmlElementFactory.getInstance(project).createTagFromText("<a>$text</a>")
        val textElements = tagFromText.value.textElements
        return if (textElements.isEmpty()) ASTFactory.composite(XmlElementType.XML_TEXT) as XmlText else textElements[0]
    }
}