package com.dvidal.core

sealed class DataState<T> {

    data class Response<T>(
        val uiComponent: UIComponent
    ): DataState<T>()

    data class Data<T>(
        val data: T? = null
    ): DataState<T>()

    data class Loading<T>(
        val pbState: ProgressBarState = ProgressBarState.Idle
    ): DataState<T>()
}