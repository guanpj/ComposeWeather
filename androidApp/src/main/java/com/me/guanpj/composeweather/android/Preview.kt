package com.me.guanpj.composeweather.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.blankj.utilcode.util.GsonUtils
import com.me.guanpj.composeweather.ForecastWeatherView
import com.me.guanpj.composeweather.bean.Daily
import com.me.guanpj.composeweather.bean.ForecastWeatherData
import com.me.guanpj.composeweather.forecaseJson

@Preview
@Composable
fun DailyWeatherViewPreview() {
    val forecastData = GsonUtils.fromJson(forecaseJson, ForecastWeatherData::class.java)
    ForecastWeatherView(forecastData)
}