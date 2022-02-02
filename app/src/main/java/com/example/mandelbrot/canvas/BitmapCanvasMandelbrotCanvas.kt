package com.example.mandelbrot.canvas

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class BitmapCanvasMandelbrotCanvas(width: Int, height: Int): BaseMandelbrotCanvas {
    override val height: Float
        get() = canvas.height.toFloat()
    override val width: Float
        get() = canvas.width.toFloat()

    val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    private val canvas: Canvas = Canvas(bitmap)

    override fun drawPoint(x: Float, y: Float, r: Int, g: Int, b: Int) {
        canvas.drawPoint(x, y, Paint().apply {
            color = Color.rgb(r, g, b)
        })
    }
}
