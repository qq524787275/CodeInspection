package com.zhuzichu.plugin.codeinspection.inspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.XmlElementVisitor
import com.intellij.psi.xml.XmlAttribute
import com.zhuzichu.plugin.codeinspection.ext.isMatchColorName


class ColorNameInspection : BaseCodeInspection() {

    override fun getDisplayName(): String {
        return "<color>标签 name属性命名检测"
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession)
            : PsiElementVisitor {
        return object : XmlElementVisitor() {
            override fun visitXmlAttribute(attribute: XmlAttribute?) {
                super.visitXmlAttribute(attribute)

                if (attribute == null) {
                    return
                }

                if (attribute.localName != "name") {
                    return
                }

                if (attribute.parent.name != "color") {
                    return
                }

                val value = attribute.value
                if (!value.isMatchColorName()) {
                    holder.registerProblem(attribute, "属性格式：c_颜色值。如：<color name=" +
                            "\"c_999999\"> #999999 </color>")
                }
            }
        }
    }

}