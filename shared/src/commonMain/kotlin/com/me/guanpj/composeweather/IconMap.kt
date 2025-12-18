package com.me.guanpj.composeweather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import composeweather.shared.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap

object IconMap : Map<String, String> by mapOf(
    "100" to "ic_100.png",
    "101" to "ic_101.png",
    "102" to "ic_102.png",
    "103" to "ic_103.png",
    "104" to "ic_104.png",
    "150" to "ic_150.png",
    "151" to "ic_151.png",
    "152" to "ic_152.png",
    "153" to "ic_153.png",
    "300" to "ic_300.png",
    "301" to "ic_301.png",
    "302" to "ic_302.png",
    "303" to "ic_303.png",
    "304" to "ic_304.png",
    "305" to "ic_305.png",
    "306" to "ic_306.png",
    "307" to "ic_307.png",
    "308" to "ic_308.png",
    "309" to "ic_309.png",
    "310" to "ic_310.png",
    "311" to "ic_311.png",
    "312" to "ic_312.png",
    "313" to "ic_313.png",
    "314" to "ic_314.png",
    "315" to "ic_315.png",
    "316" to "ic_316.png",
    "317" to "ic_317.png",
    "318" to "ic_318.png",
    "350" to "ic_350.png",
    "351" to "ic_351.png",
    "399" to "ic_399.png",
    "400" to "ic_400.png",
    "401" to "ic_401.png",
    "402" to "ic_402.png",
    "403" to "ic_403.png",
    "404" to "ic_404.png",
    "405" to "ic_405.png",
    "406" to "ic_406.png",
    "407" to "ic_407.png",
    "408" to "ic_408.png",
    "409" to "ic_409.png",
    "410" to "ic_410.png",
    "456" to "ic_456.png",
    "457" to "ic_457.png",
    "499" to "ic_499.png",
    "500" to "ic_500.png",
    "501" to "ic_501.png",
    "502" to "ic_502.png",
    "503" to "ic_503.png",
    "504" to "ic_504.png",
    "507" to "ic_507.png",
    "508" to "ic_508.png",
    "509" to "ic_509.png",
    "510" to "ic_510.png",
    "511" to "ic_511.png",
    "512" to "ic_512.png",
    "513" to "ic_513.png",
    "514" to "ic_514.png",
    "515" to "ic_515.png",
    "900" to "ic_900.png",
    "901" to "ic_901.png",
    "999" to "ic_999.png",
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun rememberIconPainter(iconCode: String, fallback: String = "100"): Painter {
    val resourceName = IconMap[iconCode] ?: IconMap[fallback] ?: error("Missing icon resource for $iconCode")
    var imageBitmap by remember(resourceName) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(resourceName) {
        try {
            val bytes = Res.readBytes("files/$resourceName")
            imageBitmap = bytes.decodeToImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return imageBitmap?.let { BitmapPainter(it) }
        ?: BitmapPainter(ImageBitmap(1, 1)) // 默认空白图片
}