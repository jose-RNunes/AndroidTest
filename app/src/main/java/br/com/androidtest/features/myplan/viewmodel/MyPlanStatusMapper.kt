package br.com.androidtest.features.myplan.viewmodel

internal fun String.toLocalizedPlanStatus(): String = when (lowercase()) {
    "active" -> "Ativo"
    else -> this
}
