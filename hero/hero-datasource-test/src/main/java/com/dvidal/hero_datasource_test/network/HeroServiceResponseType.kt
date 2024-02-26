package com.dvidal.hero_datasource_test.network

sealed class HeroServiceResponseType {

    data object EmptyData: HeroServiceResponseType()
    data object MalformedData: HeroServiceResponseType()

    data object ValidData: HeroServiceResponseType()
    data object Http404: HeroServiceResponseType()
}