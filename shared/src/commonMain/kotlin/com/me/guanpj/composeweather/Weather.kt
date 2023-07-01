package com.me.guanpj.composeweather

import com.me.guanpj.composeweather.bean.AllWeatherData
import com.me.guanpj.composeweather.bean.ForecastWeatherData
import com.me.guanpj.composeweather.bean.NowWeatherData
import com.me.guanpj.composeweather.db.Database
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class Weather {
    companion object {
        private const val PATH_NOW_WEATHER = "/now"
        private const val PATH_WEATHER_FORECAST_7D = "/7d"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    private val database = Database()

    suspend fun getAllFromDb(): AllWeatherData? {
        val now = getNowWeatherFromDb()
        val forecast = get7DWeatherFromDb()
        if (now != null && forecast != null) {
            return AllWeatherData(now, forecast)
        }
        return null
    }

    suspend fun getNowWeatherFromDb(): NowWeatherData? {
        return withContext(Dispatchers.Default) {
            database.getNowWeather()
        }
    }

    suspend fun get7DWeatherFromDb(): ForecastWeatherData? {
        return withContext(Dispatchers.Default) {
            database.getForecastWeather()
        }
    }

    suspend fun getAllFromNet(location: String = "101210101"): AllWeatherData {
        return AllWeatherData(getNowWeatherFromNet(location), get7DWeatherFromNet(location))
    }

    suspend fun getNowWeatherFromNet(location: String): NowWeatherData =
        httpClient
            .get(assembleUrl(PATH_NOW_WEATHER, location))
            .body<NowWeatherData>()
            .also {
                database.insertNowWeather(it)
            }

    suspend fun get7DWeatherFromNet(location: String): ForecastWeatherData =
        httpClient
            .get(assembleUrl(PATH_WEATHER_FORECAST_7D, location))
            .body<ForecastWeatherData>()
            .also {
                database.insertForecastWeather(it)
            }

    private fun assembleUrl(path: String, location: String): String = URLBuilder(
        protocol = URLProtocol.HTTPS,
        host = "devapi.qweather.com",
    ).apply {
        encodedPath = "/v7/weather${path}"
        encodedParameters.append("key", "dd01315398b64840a1765e5674e27f8f")
        encodedParameters.append("location", location)
    }.buildString()

}