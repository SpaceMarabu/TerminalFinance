package com.example.terminalfinance.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import com.example.terminalfinance.data.Bar
import kotlin.math.min
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun Terminal(bars: List<Bar>) {
    var visibleBarsCount by remember {
        mutableStateOf(50)
    }

    var terminalWidth by remember {
        mutableStateOf(0f)
    }

    val barWidth by remember {//стейт который завист от других стейтов
        derivedStateOf {
            terminalWidth / visibleBarsCount
        }
    }

    var scrolledBy by remember {
        mutableStateOf(0f)
    }

    val visibleBars by remember {//видимые после скролла элементы
        derivedStateOf {
            val startIndex = (scrolledBy / barWidth).roundToInt().coerceAtLeast(0)
            val endIndex = (startIndex + visibleBarsCount).coerceAtMost(bars.size)
            bars.subList(startIndex, endIndex)
        }
    }

    val trasformableState = TransformableState { zoomChange, panChange, _ -> //считывание зума и скрола
        visibleBarsCount = (visibleBarsCount / zoomChange).roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        scrolledBy = (scrolledBy + panChange.x)
            .coerceIn(0f, bars.size * barWidth - terminalWidth)
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .transformable(trasformableState)
    ) {
        terminalWidth = size.width
        val maxPrice = visibleBars.maxOf { it.high }
        val minPrice = visibleBars.minOf { it.low }
        val pixelPerPoint = size.height / (maxPrice - minPrice)
        translate(left = scrolledBy) {//смещение картинки при скроле
            bars.forEachIndexed {index, bar ->
                val offsetX = size.width - index * barWidth
                drawLine(
                    color = Color.White,
                    start = Offset(offsetX, size.height - ((bar.low - minPrice) * pixelPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.high - minPrice) * pixelPerPoint)),
                    strokeWidth = 1f
                )
                drawLine(
                    color = if (bar.open < bar.close) Color.Green else Color.Red,
                    start = Offset(offsetX, size.height - ((bar.open - minPrice) * pixelPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.close - minPrice) * pixelPerPoint)),
                    strokeWidth = barWidth / 2
                )
            }
        }
    }
}