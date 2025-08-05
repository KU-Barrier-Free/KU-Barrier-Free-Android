package com.ganaljigi.kubf.ui.common.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StrokeText(
    text: String,
    style: TextStyle = TextStyle.Default,
    strokeColor: Color = Color.Black,
    strokeWidth: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val fontSizePx = with(density) { style.fontSize.toPx() }
    val strokeWidthPx = with(density) { strokeWidth.toPx() }

    Canvas(modifier = modifier) {
        drawIntoCanvas { canvas ->

            // 텍스트 스타일 속성 추출
            val color = style.color.takeOrElse { Color.Black }

            val strokePaint = Paint().apply {
                isAntiAlias = true
                this.style = Paint.Style.STROKE
                this.strokeWidth = strokeWidthPx
                this.color = strokeColor.toArgb()
                textSize = fontSizePx
                textAlign = Paint.Align.LEFT
            }

            val fillPaint = Paint().apply {
                isAntiAlias = true
                this.style = Paint.Style.FILL
                this.color = color.toArgb()
                textSize = fontSizePx
                textAlign = Paint.Align.LEFT
            }

            val x = 0f
            val y = fontSizePx // 텍스트 baseline에 맞게

            canvas.nativeCanvas.drawText(text, x, y, strokePaint)
            canvas.nativeCanvas.drawText(text, x, y, fillPaint)
        }
    }
}