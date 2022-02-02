package com.example.mandelbrot.canvas

import android.graphics.Bitmap
import android.graphics.Color

class BitmapMandelbrotCanvas(width: Int, height: Int): BaseMandelbrotCanvas {
    override val height: Float
        get() = bitmap.height.toFloat()
    override val width: Float
        get() = bitmap.width.toFloat()

    val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    override fun drawPoint(x: Float, y: Float, r: Int, g: Int, b: Int) {
        bitmap.setPixel(x.toInt(), y.toInt(), Color.rgb(r, g, b))
    }
}
