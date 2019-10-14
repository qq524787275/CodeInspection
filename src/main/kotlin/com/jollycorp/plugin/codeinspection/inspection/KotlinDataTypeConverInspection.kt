package com.jollycorp.plugin.codeinspection.inspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.jollycorp.plugin.codeinspection.ext.catchesNumberFormatException
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtTryExpression
import org.jetbrains.kotlin.psi.KtVisitorVoid

/**
 * kotlin代码 类型转换检测
 */
class KotlinDataTypeConverInspection : BaseKotlinCodeInspection() {

    override fun getDisplayName(): String {
        return "kotlin 数据类型转换检测"
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession)
            : PsiElementVisitor {
        return object : KtVisitorVoid() {
            override fun visitCallExpression(expression: KtCallExpression) {
                super.visitCallExpression(expression)

                if (!checkMethodIsDataConvert(expression)) {
                    return
                }

                val tryElement = PsiTreeUtil.getParentOfType(expression, KtTryExpression::class.java)

                if (!tryElement.catchesNumberFormatException()) {
                    holder.registerProblem(expression, "可能会报NumberFormatException异常,请使用" +
                            " ToolNumber 里的方法替代,或者用 try catch(NumberFormatException) 包裹起来")
                }

            }
        }
    }

    /**
     * 判断Kotlin代码中是不是类型转换的方法
     */
    private fun checkMethodIsDataConvert(expression: KtCallExpression): Boolean {
        val text = expression.text
        if (
                text.contains("toDouble()")
                || text.contains("toInt()")
                || text.contains("toShort()")
                || text.contains("toByte()")
                || text.contains("toLong()")
                || text.contains("toFloat()")
                || text.contains("toBoolean()")
        ) {
            return true
        }
        return false
    }
}