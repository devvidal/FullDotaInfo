package com.dvidal.core

sealed class ProgressBarState {

    object Loading: ProgressBarState()
    object Idle: ProgressBarState()
}