package com.dvidal.ui_herolist.ui

sealed class HeroListEvents {

    data object GetHeros: HeroListEvents()
}