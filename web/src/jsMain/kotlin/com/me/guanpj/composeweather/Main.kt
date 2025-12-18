package com.me.guanpj.composeweather

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.getElementById("ComposeTarget")!!) {
        MaterialTheme(
            colors = lightColors(
                primary = Color(0xFF6200EE),
                primaryVariant = Color(0xFF3700B3),
                secondary = Color(0xFF03DAC5),
                background = Color.White
            ),
            typography = Typography(
                defaultFontFamily = FontFamily.SansSerif,
                body1 = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
        ) {
            Surface(color = MaterialTheme.colors.background) {
                WeatherPageView()
            }
        }
    }
}
