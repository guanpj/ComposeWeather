package com.me.guanpj.composeweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.me.guanpj.composeweather.bean.Daily
import com.me.guanpj.composeweather.bean.ForecastWeatherData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun ForecastWeatherView(data: ForecastWeatherData) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF7F8F9),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        data.daily.drop(1).forEach { daily ->
            DailyWeatherView(daily)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DailyWeatherView(daily: Daily) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = daily.fxDate, color = Color.Black, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(30.dp))
        Icon(
            painter = painterResource(
                res = IconMap[daily.iconDay] ?: "ic_100.png",
            ),
            contentDescription = daily.textDay,
            modifier = Modifier
                .width(20.dp)
                .height(20.dp),
            tint = Color.Blue
        )
        Text(
            modifier = Modifier.weight(1f),
            text = daily.textDay,
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "${daily.tempMin}℃-${daily.tempMax}℃",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}