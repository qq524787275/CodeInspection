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

class StringNameInspection : BaseCodeInspection() {

    override fun getDisplayName(): String {
        return "<string> name属性命名检测"
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

                if (attribute.parent.name != "string") {
                    return
                }

                val value = attribute.value

                if (!value.isNullOrBlank() && !value[0].isLetter()) {
                    holder.registerProblem(
                            attribute,
                            "请使用小写字母开头 格式：页面名称(或者简写)_{类型}_{实际意义单词},如:" +
                                    "页面标题：title。如FragmentGoodsDetail的标题：goods_detail_title"
                    )
                    return
                }

                if (!value.isNullOrBlank() && value.isHump()) {
                    holder.registerProblem(
                            attribute,
                            "请使用下划线_替代驼峰 格式：页面名称(或者简写)_{类型}_{实际意义单词},如" +
                                    ":页面标题：title。如FragmentGoodsDetail的标题：goods_detail_title",
                            GenerateMethod(attribute.createSmartPointer(), value)
                    )
                }
            }
        }
    }


    /**
     * 驼峰转下划线快捷方式
     */
    inner class GenerateMethod(private val attribute: SmartPsiElementPointer<XmlAttribute>, private val value: String)
        : LocalQuickFix {
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