package com.jollycorp.plugin.codeinspection.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel

/**
 * Inspection接口 初始化默认信息
 */
interface IBaseInspection {

    companion object {
        const val GROUP_NAME = "JY-Code-Inspection"
    }

    fun getGroupName(): String = GROUP_NAME

    fun getLevel(): HighlightDisplayLevel = HighlightDisplayLevel.ERROR

    fun isEnabled(): Boolean = true
}
