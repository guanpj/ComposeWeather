package com.me.guanpj.composeweather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.me.guanpj.composeweather.bean.AllWeatherData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel {
    private val weather = Weather()
    var status by mutableStateOf<PageState>(PageState.Init)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        status = PageState.Fail(exception.message.toString())
    }

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + coroutineExceptionHandler + job)

    fun getWeatherFromNet(location: String = "101280601") {
        status = PageState.Loading
        viewModelScope.launch {
            val result = try {
                PageState.Success(weather.getAllFromNet(location))
            } catch (e: Exception) {
                PageState.Fail(e.message ?: "Unknown error")
            }
            status = result
        }
    }

    fun getWeatherFromCache() {
        status = PageState.Loading
        viewModelScope.launch {
            val result = try {
                delay(1000)
                val data = weather.getAllFromDb()
                if (data == null) {
                    PageState.Fail("no cache")
                } else {
                    PageState.Success(data)
                }
            } catch (e: Exception) {
                PageState.Fail("error")
            }
            status = result
        }
    }

    sealed class PageState {
        object Init : PageState()
        object Loading : PageState()
        class Success(val data: AllWeatherData) : PageState()
        class Fail(val message: String) : PageState()
    }
}