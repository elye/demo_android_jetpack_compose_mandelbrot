package com.example.mandelbrot.drawing

import com.example.mandelbrot.canvas.BaseMandelbrotCanvas

open class MandelbrotCanvasCustom {

    companion object {
        private const val MAX_ITER = 1000
        private const val ZOOM = 1000
    }

    data class MandelbrotPoint(
        val x: Float, val y: Float,
        val r: Int, val g: Int, val b: Int
    )

    open fun draw(canvas: BaseMandelbrotCanvas, update: () -> Unit = { }) {
        for (y in 0 until canvas.height.toInt()) {
            for (x in 0 until canvas.width.toInt()) {
                drawMandelbrotPoint(x, canvas, y)
            }
        }
    }

    private fun drawMandelbrotPoint(x: Int, canvas: BaseMandelbrotCanvas, y: Int, update: () -> Unit = { }) {
        val iter = calculateIter(x, canvas, y)
        canvas.drawPoint(createMandelbrotPoint(x, y, canvas, iter))
        update()
    }

    protected fun createMandelbrotPoint(
        x: Int,
        y: Int,
        canvas: BaseMandelbrotCanvas,
        iter: Int
    ) = MandelbrotPoint(
        x.toFloat(), y.toFloat(),
        (x.toFloat() / canvas.width * 0xFF).toInt(),
        (y.toFloat() / canvas.height * 0xFF).toInt(),
        ((iter or (iter shl 7)).toFloat()
                / ((MAX_ITER or MAX_ITER shl 7)).toFloat() * 0xFF).toInt()
    )

    protected fun calculateIter(
        x: Int,
        canvas: BaseMandelbrotCanvas,
        y: Int
    ): Int {
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
        return iter
    }
}
