package com.example.mandelbrot.drawing

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class NativeMandelbrotCanvas(private val canvas: Canvas): MandelbrotCanvasCustom.MandelbrotCanvas {
    override val height: Float
        get() = canvas.height.toFloat()
    override val width: Float
        get() = canvas.width.toFloat()

    override fun drawPoint(x: Float, y: Float, r: Int, g: Int, b: Int) {
        canvas.drawPoint(x, y, Paint().apply {
            color = Color.rgb(r, g, b)
        })
    }
}
