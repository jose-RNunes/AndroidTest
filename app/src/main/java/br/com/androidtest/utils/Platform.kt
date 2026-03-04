package br.com.androidtest.utils

enum class Platform(val code: String) {
    NP("NP"),
    RW("RW");

    companion object {
        fun fromCode(code: String?): Platform =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: NP
    }
}