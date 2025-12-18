package com.me.guanpj.composeweather

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.me.guanpj.composeweather.bean.AllWeatherData
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun WeatherPageView() {
    val viewModel by remember { mutableStateOf(WeatherViewModel()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF3700B3))
        ) {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Compose Weather",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Cache",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        viewModel.getWeatherFromCache()
                    })
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Net",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        viewModel.getWeatherFromNet()
                    })
            }
        }
        when (viewModel.status) {
            WeatherViewModel.PageState.Init -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "Please select data source", modifier = Modifier.align(Alignment.Center))
                }
            }
            WeatherViewModel.PageState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.Center)
                    )
                }
            }
            is WeatherViewModel.PageState.Success -> {
                val data = (viewModel.status as WeatherViewModel.PageState.Success).data
                WeatherView(data = data)
            }
            is WeatherViewModel.PageState.Fail -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    val message = (viewModel.status as WeatherViewModel.PageState.Fail).message
                    Text(text = message, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun WeatherView(data: AllWeatherData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        NowWeatherView(data.now)
        Spacer(modifier = Modifier.height(16.dp))
        ForecastWeatherView(data = data.forecast)
    }
}
