package com.me.guanpj.composeweather.bean

import kotlinx.serialization.Serializable

@Serializable
data class AllWeatherData(
    val now: NowWeatherData,
    val forecast: ForecastWeatherData
)
