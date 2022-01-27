package com.example.mandelbrot.drawing

import com.example.mandelbrot.canvas.BaseMandelbrotCanvas
import kotlinx.coroutines.*

class MandelbrotCanvasCustomChannelIncremental: MandelbrotCanvasCustomChannel() {

    private fun parallel(canvas: BaseMandelbrotCanvas, update: () -> Unit) = CoroutineScope(Dispatchers.Default).launch {
        fanoutFanin(canvas, update)
    }

    override fun draw(canvas: BaseMandelbrotCanvas, update: () -> Unit) {
        parallel(canvas, update)
    }
}
