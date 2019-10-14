package com.jollycorp.plugin.codeinspection.inspection

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiTryStatement
import com.intellij.psi.util.PsiTreeUtil
import com.jollycorp.plugin.codeinspection.ext.catchesNumberFormatException


/**
 * java代码 类型转换检测
 */
class JavaDataTypeConvertInspection : BaseCodeInspection() {

    override fun getDisplayName(): String {
        return "java 数据类型转换检测"
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {

        return object : JavaElementVisitor() {

            override fun visitMethodCallExpression(expression: PsiMethodCallExpression?) {
                super.visitMethodCallExpression(expression)

                if (expression == null) {
                    return
                }
                if (!checkMethodIsDataConvert(expression)) {
                    return
                }
                val tryElement = PsiTreeUtil.getParentOfType(expression, PsiTryStatement::class.java)

                if (!tryElement.catchesNumberFormatException()) {
                    holder.registerProblem(expression, "可能会报NumberFormatException异常,请使用 ToolNumber 里的方法替代,或者用 try catch(NumberFormatException) 包裹起来")
                }

            }
        }
    }

    /**
     * 判断Java代码中是不是类型转换的方法
     */
    private fun checkMethodIsDataConvert(expression: PsiMethodCallExpression): Boolean {
        val text = expression.text
        if (
                text.contains("Integer.parseInt(")
                || text.contains("Double.parseDouble(")
                || text.contains("Short.parseShort(")
                || text.contains("Byte.parseByte(")
                || text.contains("Long.parseLong(")
                || text.contains("Float.parseFloat(")
                || text.contains("Boolean.parseBoolean(")
                || text.contains("Integer.valueOf(")
                || text.contains("Double.valueOf(")
                || text.contains("Short.valueOf(")
                || text.contains("Byte.valueOf(")
                || text.contains("Long.valueOf(")
                || text.contains("Float.valueOf(")
                || text.contains("Boolean.valueOf(")
        ) {
            return true
        }
        return false
    }
}