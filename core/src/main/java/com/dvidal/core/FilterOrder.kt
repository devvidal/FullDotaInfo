package com.dvidal.core

sealed class FilterOrder {

    data object Ascending: FilterOrder()
    data object Descending: FilterOrder()
}