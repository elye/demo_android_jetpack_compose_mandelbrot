package com.example.mandelbrot

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log


class MandelbrotCanvasCustom {

    companion object {
        private const val MAX_ITER = 300
        private const val ZOOM = 300
    }

    fun draw(canvas: Canvas) {
        for (y in 0 until canvas.height) {
            for (x in 0 until canvas.width) {
                drawMandelbrotPoint(x, canvas, y)
            }
        }
    }

    private fun drawMandelbrotPoint(x: Int, canvas: Canvas, y: Int) {
        var zx = 0.0
        var zy = 0.0
        val cX = (x - canvas.width.toFloat() / 2) / ZOOM
        val cY = (y - canvas.height.toFloat() / 2) / ZOOM
        var iter = MAX_ITER
        while (zx * zx + zy * zy < 4.0 && iter > 0) {
            val tmp = zx * zx - zy * zy + cX
            zy = 2.0 * zx * zy + cY
            zx = tmp
            iter--
        }

        canvas.drawPoint(x.toFloat(), y.toFloat(),
            Paint().apply {
                color = Color.rgb(
                    ((x.toFloat() / canvas.width.toFloat()) * 0xFF).toInt(),
                    ((y.toFloat() / canvas.height.toFloat()) * 0xFF).toInt(),
                    ((iter or (iter shl 7)).toFloat()
                            / ((MAX_ITER or MAX_ITER shl 7)) * 0xFF).toInt()
                )
            }
        )
    }
}
