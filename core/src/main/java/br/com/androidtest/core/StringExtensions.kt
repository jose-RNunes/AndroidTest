package br.com.androidtest.core

fun String.getDescriptionIconFromUrl(): String {
    return this.split("-alternative").last()
}

fun String.maskCpf(): String {
    return try {
        this.replaceRange(3, 9, "***.***")
    } catch (_: Exception) {
        this
    }

}
