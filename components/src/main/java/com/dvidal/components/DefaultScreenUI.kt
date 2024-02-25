package com.dvidal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dvidal.core.ProgressBarState

@Composable
fun DefaultScreenUI(
    pbState: ProgressBarState = ProgressBarState.Idle,
    content: @Composable () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        it.calculateTopPadding()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            content.invoke()

            if (pbState is ProgressBarState.Loading) {
                CircularIndeterminateProgressBar()
            }
        }
    }
}