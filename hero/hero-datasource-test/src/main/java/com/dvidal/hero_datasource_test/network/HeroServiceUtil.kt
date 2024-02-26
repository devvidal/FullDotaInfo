@file:OptIn(ExperimentalSerializationApi::class)

package com.dvidal.hero_datasource_test.network

import com.dvidal.hero_datasource.network.HeroDto
import com.dvidal.hero_datasource.network.toHero
import com.dvidal.hero_domain.Hero
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


private val json = Json {
    ignoreUnknownKeys = true
}

fun serializeHeroData(jsonData: String): List<Hero> {
    return json.decodeFromString<List<HeroDto>>(jsonData).map { it.toHero() }
}