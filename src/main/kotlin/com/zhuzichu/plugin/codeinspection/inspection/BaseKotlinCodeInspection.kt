package com.zhuzichu.plugin.codeinspection.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection

/**
 * kotlin 代码检测 Inspection基类
 */
abstract class BaseKotlinCodeInspection : AbstractKotlinInspection(), IBaseInspection {

    override fun getGroupDisplayName(): String {
        return getGroupName()
    }

    override fun isEnabledByDefault(): Boolean {
        return isEnabled()
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return getLevel()
    }

}