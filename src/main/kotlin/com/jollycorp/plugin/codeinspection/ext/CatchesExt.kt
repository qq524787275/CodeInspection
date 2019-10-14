package com.jollycorp.plugin.codeinspection.ext

import com.intellij.psi.PsiTryStatement
import org.jetbrains.kotlin.psi.KtTryExpression

/**
 * java代码 检测try catch 是否有NumberFormatException
 */
fun PsiTryStatement?.catchesNumberFormatException(): Boolean {
    if (this == null) {
        return false
    }
    val sections = this.catchSections
    for (section in sections) {
        val catchType = section.catchType
        if (catchType != null) {
            val typeText = catchType.canonicalText
            if (
                    "java.lang.NumberFormatException" == typeText
                    || "java.lang.Exception" == typeText
                    || "java.lang.IllegalArgumentException" == typeText
                    || "java.lang.RuntimeException" == typeText) {
                return true
            }
        }
    }
    return false
}

/**
 * kotlin代码 检测try catch 是否有NumberFormatException
 */
fun KtTryExpression?.catchesNumberFormatException(): Boolean {
    if (this == null) {
        return false
    }
    val clauses = this.catchClauses
    for (clause in clauses) {
        val catchParameter = clause.catchParameter
        if (catchParameter != null) {
            val typeText = catchParameter.typeReference?.text ?: ""
            if (
                    "NumberFormatException" == typeText
                    || "Exception" == typeText
                    || "IllegalArgumentException" == typeText
                    || "RuntimeException" == typeText
                    || "java.lang.NumberFormatException" == typeText
                    || "java.lang.Exception" == typeText
                    || "java.lang.IllegalArgumentException" == typeText
                    || "java.lang.RuntimeException" == typeText) {
                return true
            }
        }
    }
    return false
}