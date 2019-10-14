package com.jollycorp.plugin.codeinspection.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.LocalInspectionTool

/**
 * java 代码检测 Inspection基类
 */
abstract class BaseCodeInspection : LocalInspectionTool(), IBaseInspection {

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