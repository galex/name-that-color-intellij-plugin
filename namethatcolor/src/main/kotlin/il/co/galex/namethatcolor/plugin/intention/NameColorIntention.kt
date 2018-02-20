package il.co.galex.namethatcolor.plugin.intention

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.XmlElementFactory
import com.intellij.psi.formatter.FormatterUtil
import com.intellij.psi.impl.source.xml.XmlTagImpl
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlText
import com.intellij.xml.util.XmlPsiUtil
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.core.util.toXmlName

class NameColorIntention(private val hexColor: HexColor) : IntentionAction {

    override fun getFamilyName(): String = "Name That Color"
    override fun getText(): String = "Name that color"
    override fun startInWriteAction(): Boolean = true
    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean = true

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {

        if (file is XmlFile) {
            file.rootTag?.let { rootTag ->
                val elements = rootTag.children.filter { it is XmlText }
                elements.forEach {oldElement ->
                    val text = oldElement.text.replace("\n", "").trim()
                    if (text == hexColor.input) {

                        val (hexColor, color) = ColorNameFinder.findColor(HexColor(hexColor.input))
                        var name = color.name.toXmlName()
                        val percentAlpha = hexColor.percentAlpha()
                        if (percentAlpha != null) {
                            name += "_$percentAlpha"
                        }

                        val insert = "<color name=\"$name\">${hexColor.inputToString()}</color>"
                        val newElement = XmlElementFactory.getInstance(project).createTagFromText(insert)

                        val split = oldElement.text.split(hexColor.input)
                        oldElement.replace(newElement)
                        if (split.isNotEmpty()) rootTag.addBefore(XmlElementFactory.getInstance(project).createDisplayText(split[0]), oldElement)
                        if (split.size > 1) rootTag.addAfter(XmlElementFactory.getInstance(project).createDisplayText(split[1].trim()), oldElement)



                        // get out of our loop if we found one to replace
                        return@forEach
                    }
                }
            }
        }
    }
}