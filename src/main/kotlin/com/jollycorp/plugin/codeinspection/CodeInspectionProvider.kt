package com.jollycorp.plugin.codeinspection

import com.google.common.collect.Lists
import com.intellij.codeInspection.InspectionToolProvider
import com.jollycorp.plugin.codeinspection.inspection.JavaDataTypeConvertInspection
import com.jollycorp.plugin.codeinspection.inspection.KotlinDataTypeConverInspection
import com.jollycorp.plugin.codeinspection.inspection.LayoutIdInspection

/**
 * Inpection提供者 在plugin.xml 中注册
 */
class CodeInspectionProvider : InspectionToolProvider {

    companion object {
        /**
         * 初始化所有Inspection
         */
        private val CLASS_LIST = Lists.newArrayList<Class<*>>().apply {
            add(LayoutIdInspection::class.java)
            add(JavaDataTypeConvertInspection::class.java)
            add(KotlinDataTypeConverInspection::class.java)
        }
    }

    override fun getInspectionClasses(): Array<Class<*>> {
        return CLASS_LIST.toTypedArray()
    }

}