package il.co.galex.namethatcolor.plugin.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.xml.XmlTextImpl
import com.intellij.psi.xml.XmlToken
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.plugin.intention.NameColorIntention
import il.co.galex.namethatcolor.plugin.util.RESOURCES_TAG_NAME

class ColorAnnotator: Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        try {
            val color = HexColor(element.text)
            // we found a value that is a valid color, checking that it is not already in a <color> tag
            if ((element.parent as XmlTextImpl).parentTag?.name != "color") {
                holder.createWarningAnnotation(element, "Name that color")
                        .registerFix(NameColorIntention(color))
            }
        } catch (e: Exception) {
            // found that a color is not valid for us, ignoring it
        }
    }
}