package com.zhuzichu.plugin.codeinspection

import com.google.common.collect.Lists
import com.intellij.codeInspection.InspectionToolProvider
import com.zhuzichu.plugin.codeinspection.inspection.*

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
            add(ColorNameInspection::class.java)
            add(StringNameInspection::class.java)
        }
    }

    override fun getInspectionClasses(): Array<Class<*>> {
        return CLASS_LIST.toTypedArray()
    }

}