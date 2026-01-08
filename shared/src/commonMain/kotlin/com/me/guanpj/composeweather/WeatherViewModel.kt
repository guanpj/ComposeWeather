package com.me.guanpj.composeweather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.me.guanpj.composeweather.bean.AllWeatherData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.TimeoutCancellationException

class WeatherViewModel {
    private val weather = Weather()
    var status by mutableStateOf<PageState>(PageState.Init)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        val errorMessage = when (exception) {
            is TimeoutCancellationException -> "请求超时，请检查网络连接"
            is CancellationException -> "请求已取消"
            else -> exception.message ?: "未知错误"
        }
        status = PageState.Fail(errorMessage)
    }

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + coroutineExceptionHandler + job)

    fun getWeatherFromNet(location: String = "101280601") {
        status = PageState.Loading
        viewModelScope.launch {
            val result = try {
                PageState.Success(weather.getAllFromNet(location))
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is TimeoutCancellationException -> "请求超时，请检查网络连接"
                    is CancellationException -> "请求已取消"
                    else -> e.message ?: "未知错误"
                }
                PageState.Fail(errorMessage)
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
                    PageState.Fail("没有缓存数据")
                } else {
                    PageState.Success(data)
                }
            } catch (e: Exception) {
                PageState.Fail("获取缓存数据失败")
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