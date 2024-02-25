package com.dvidal.core

sealed class UiComponentState {

    data object Show: UiComponentState()
    data object Hide: UiComponentState()
}