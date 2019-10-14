package com.zhuzichu.plugin.codeinspection.ext

import java.util.regex.Pattern

const val REGEX_COLOR_NAME = "^(c_)[A-Fa-f0-9]{6}"

fun String?.isMatch(regex: String): Boolean {
    return (!isNullOrBlank() && Pattern.matches(regex, this))
}

fun String?.isMatchColorName() = this.isMatch(REGEX_COLOR_NAME)