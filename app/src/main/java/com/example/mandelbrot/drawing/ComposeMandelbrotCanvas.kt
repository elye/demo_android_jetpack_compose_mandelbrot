package com.example.mandelbrot.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope

class ComposeMandelbrotCanvas(private val canvas: DrawScope): MandelbrotCanvas {
    override val height: Float
        get() = canvas.size.height
    override val width: Float
        get() = canvas.size.width

    override fun drawPoint(x: Float, y: Float, r: Int, g: Int, b: Int) {
        canvas.drawPoints(
            listOf(Offset(x, y)),
            PointMode.Points,
            Color(r, g, b),
            1f
        )
    }
}