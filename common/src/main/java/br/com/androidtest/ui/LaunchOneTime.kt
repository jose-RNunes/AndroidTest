package br.com.androidtest.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun LaunchOneTime(block: suspend CoroutineScope.() -> Unit) {
    var key by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        if (!key) {
            key = true
            block()
        }
    }

}
