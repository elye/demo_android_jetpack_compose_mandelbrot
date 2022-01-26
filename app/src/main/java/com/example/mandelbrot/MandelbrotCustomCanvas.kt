package com.example.mandelbrot

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint


class MandelbrotCanvasCustom {

    companion object {
        private const val MAX_ITER = 300
        private const val ZOOM = 300
    }

    fun draw(canvas: MandelbrotCanvas) {
        for (y in 0 until canvas.height.toInt()) {
            for (x in 0 until canvas.width.toInt()) {
                drawMandelbrotPoint(x, canvas, y)
            }
        }
    }

    private fun drawMandelbrotPoint(x: Int, canvas: MandelbrotCanvas, y: Int) {
        var zx = 0.0
        var zy = 0.0
        val cX = (x - canvas.width / 2) / ZOOM
        val cY = (y - canvas.height / 2) / ZOOM
        var iter = MAX_ITER
        while (zx * zx + zy * zy < 4.0 && iter > 0) {
            val tmp = zx * zx - zy * zy + cX
            zy = 2.0 * zx * zy + cY
            zx = tmp
            iter--
        }

        canvas.drawPoint(x.toFloat(), y.toFloat(), (iter or (iter shl 7)).toFloat()
                / ((MAX_ITER or MAX_ITER shl 7)).toFloat())
    }

    interface MandelbrotCanvas {
        val height: Float
        val width: Float
        fun drawPoint(x: Float, y: Float, iter: Float)
    }
}

class NativeMandelbrotCanvas(private val canvas: Canvas): MandelbrotCanvasCustom.MandelbrotCanvas {
    override val height: Float
        get() = canvas.height.toFloat()
    override val width: Float
        get() = canvas.width.toFloat()

    override fun drawPoint(x: Float, y: Float, iter: Float) {
        canvas.drawPoint(x, y,
            Paint().apply {
                color = Color.rgb(
                    ((x / canvas.width) * 0xFF).toInt(),
                    ((y / canvas.height) * 0xFF).toInt(),
                    (iter * 0xff).toInt()
                )
            })
    }
}
