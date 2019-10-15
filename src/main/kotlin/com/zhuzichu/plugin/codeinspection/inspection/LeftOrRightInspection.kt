package com.zhuzichu.plugin.codeinspection.inspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.SmartPsiElementPointer
import com.intellij.psi.XmlElementVisitor
import com.intellij.psi.xml.XmlAttribute
import com.zhuzichu.plugin.codeinspection.ext.hump2Underline
import org.jetbrains.kotlin.idea.util.application.runWriteAction
import org.jetbrains.kotlin.psi.psiUtil.createSmartPointer

class LeftOrRightInspection : BaseCodeInspection() {

    override fun getDisplayName(): String {
        return "布局文件 left right 属性检测"
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession)
            : PsiElementVisitor {
        return object : XmlElementVisitor() {
            override fun visitXmlAttribute(attribute: XmlAttribute?) {
                super.visitXmlAttribute(attribute)
                if (attribute == null) {
                    return
                }



                if (attribute.parent.parentTag?.name != "androidx.constraintlayout.widget.ConstraintLayout") {
                    return
                }

                if (attribute.name.contains("Left")) {
                    holder.registerProblem(attribute, "请使用End替换Left",
                            GenerateMethod(attribute.createSmartPointer(),
                                    attribute.name,
                                    "Left",
                                    "End")
                    )
                    return
                }

                if (attribute.name.contains("Right")) {
                    holder.registerProblem(attribute, "请使用Start替换Right",
                            GenerateMethod(attribute.createSmartPointer(),
                                    attribute.name,
                                    "Right",
                                    "Start")
                    )
                }

            }
        }
    }

    /**
     * 驼峰转下划线快捷方式
     */
    inner class GenerateMethod(
            private val attribute: SmartPsiElementPointer<XmlAttribute>,
            private val name: String,
            private val src: String,
            private val dst: String
    ) : LocalQuickFix {

        override fun getName(): String = src.plus("替换").plus(dst)

        override fun getFamilyName(): String = "check left or right"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            attribute.element?.name = name.replace(src, dst)
            runWriteAction {
                attribute
            }
        }
    }
}