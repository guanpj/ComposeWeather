package com.me.guanpj.composeweather.db

import com.me.guanpj.composeweather.bean.ForecastWeatherData
import com.me.guanpj.composeweather.bean.NowWeatherData
import com.me.guanpj.composeweather.db.AppDatabase
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class Database {
    private val database = AppDatabase(createDriver(
        schema = AppDatabase.Schema,
        dbName = "compose_weather.db"
    ))

    private val dbQuery = database.appDatabaseQueries

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }
    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllNowWeather()
            dbQuery.removeAllForecastWeather()
        }
    }
    internal fun insertNowWeather(data: NowWeatherData) {
        dbQuery.insertNowWeather(json.encodeToString(data))
    }

    internal fun getNowWeather(): NowWeatherData? {
        val entity = dbQuery.selectNowWeather().executeAsOneOrNull()
        if (entity?.json?.isNotEmpty() == true) {
            return json.decodeFromString(entity.json)
        }
        return null
    }

    internal fun insertForecastWeather(data: ForecastWeatherData) {
        dbQuery.insertForecastWeather(json.encodeToString(data))
    }

    internal fun getForecastWeather(): ForecastWeatherData? {
        val entity = dbQuery.selectForecastWeather().executeAsOneOrNull()
        if (entity?.json?.isNotEmpty() == true) {
            return json.decodeFromString(entity.json)
        }
        return null
    }
}