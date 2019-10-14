package com.jollycorp.plugin.codeinspection.inspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.SmartPsiElementPointer
import com.intellij.psi.XmlElementVisitor
import com.intellij.psi.xml.XmlAttribute
import com.jollycorp.plugin.codeinspection.ext.hump2Underline
import com.jollycorp.plugin.codeinspection.ext.isHump
import org.jetbrains.kotlin.idea.util.application.runWriteAction
import org.jetbrains.kotlin.psi.psiUtil.createSmartPointer

/**
 * xml代码 layout中布局id 命名检测
 */
class LayoutIdInspection : BaseCodeInspection() {

    override fun getDisplayName(): String {
        return "资源id命名检测"
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {
        return object : XmlElementVisitor() {
            override fun visitXmlAttribute(attribute: XmlAttribute?) {
                super.visitXmlAttribute(attribute)
                if (attribute?.localName != "id") {
                    return
                }

                val value = attribute.value?.split("/")?.get(1)

                if (!value.isNullOrBlank() && !value[0].isLetter()) {
                    holder.registerProblem(
                            attribute,
                            "请使用小写字母开头"
                    )
                }

                if (!value.isNullOrBlank() && value.isHump()) {
                    holder.registerProblem(
                            attribute,
                            "请使用下划线_替代驼峰",
                            GenerateMethod(attribute.createSmartPointer(), value)
                    )
                }
            }
        }
    }

    /**
     * 驼峰转下划线快捷方式
     */
    inner class GenerateMethod(private val attribute: SmartPsiElementPointer<XmlAttribute>, private val value: String) : LocalQuickFix {
        override fun getName(): String = "驼峰转下划线"

        override fun getFamilyName(): String = "check resource id value"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            attribute.element?.setValue("@+id/".plus(value.hump2Underline()))
            runWriteAction {
                attribute
            }
        }
    }

}