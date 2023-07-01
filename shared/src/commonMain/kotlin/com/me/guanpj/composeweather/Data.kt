package com.me.guanpj.composeweather

import androidx.compose.runtime.mutableStateOf

val status = mutableStateOf<WeatherViewModel.PageState>(WeatherViewModel.PageState.Init)