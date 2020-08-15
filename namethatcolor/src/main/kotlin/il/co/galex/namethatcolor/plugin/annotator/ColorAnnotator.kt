package il.co.galex.namethatcolor.plugin.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.xml.XmlTextImpl
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.plugin.intention.NameColorIntention
import il.co.galex.namethatcolor.plugin.util.*

class ColorAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        try {
            val color = HexColor(element.text)
            // we found a value that is a valid color, checking that it is not already in a <color> tag
            if ((element.parent as XmlTextImpl).parentTag?.name == RESOURCES_TAG_NAME) {

                // color list from name that color
                holder.newAnnotation(HighlightSeverity.ERROR, COLOR_ANNOTATION_MESSAGE)
                        .newFix(NameColorIntention(NAME_THAT_COLOR, color, ColorNameFinder::findColor))
                        .registerFix()
                        .create()

                // material color palette
                holder.newAnnotation(HighlightSeverity.ERROR, MATERIAL_COLOR_ANNOTATION_MESSAGE)
                        .newFix(NameColorIntention(NAME_THAT_MATERIAL_COLOR, color, ColorNameFinder::findMaterialColor))
                        .registerFix()
                        .create()
            }
        } catch (e: Exception) {
            // found that a color is not valid for us, ignoring it
        }
    }
}