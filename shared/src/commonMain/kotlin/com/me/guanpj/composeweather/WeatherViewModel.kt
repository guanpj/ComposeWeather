package com.me.guanpj.composeweather

import androidx.compose.runtime.mutableStateOf
import com.me.guanpj.composeweather.bean.AllWeatherData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object WeatherViewModel {
    private val weather = Weather()
    val status = mutableStateOf<PageState>(PageState.Init)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        status.value = PageState.Fail(exception.message.toString())
    }

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + coroutineExceptionHandler + job)

    fun getWeatherFromNet(location: String = "101280601") {
        status.value = PageState.Loading
        viewModelScope.launch {
            val result = try {
                PageState.Success(weather.getAllFromNet(location))
            } catch (e: Exception) {
                PageState.Fail(e.message ?: "Unknown error")
            }
            status.value = result
        }
    }

    fun getWeatherFromCache() {
        status.value = PageState.Loading
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
            status.value = result
        }
    }

    sealed class PageState {
        object Init : PageState()
        object Loading : PageState()
        class Success(val data: AllWeatherData) : PageState()
        class Fail(val message: String) : PageState()
    }
}