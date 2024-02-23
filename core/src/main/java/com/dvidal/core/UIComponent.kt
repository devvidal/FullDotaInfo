package com.dvidal.core

sealed interface UIComponent {

    data class Dialog(
        val title: String,
        val description: String
    ): UIComponent

    data class None(
        val message: String
    ): UIComponent
}