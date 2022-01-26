package com.example.mandelbrot.drawing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Flow

class MandelbrotCanvasCustom {

    companion object {
        private const val MAX_ITER = 300
        private const val ZOOM = 300
    }

    data class MandelbrotPoint(
        val x: Float, val y: Float,
        val r: Int, val g: Int, val b: Int
    )

    fun parallel(canvas: MandelbrotCanvas) = runBlocking {
        val consumer = Channel<MandelbrotPoint>()
        accumulateProducer(consumer, canvas)
        accumulateConsumer(consumer).join()
    }

    fun CoroutineScope.accumulateProducer(
        consumer: Channel<MandelbrotPoint>,
        canvas: MandelbrotCanvas) =
        produce<Double>(Dispatchers.Default) {
            for (y in 0 until canvas.height.toInt()) {
                for (x in 0 until canvas.width.toInt()) {

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

                    canvas.drawPoint(
                        x.toFloat(), y.toFloat(),
                        (x.toFloat() / canvas.width * 0xFF).toInt(),
                        (y.toFloat() / canvas.height * 0xFF).toInt(),
                        ((iter or (iter shl 7)).toFloat()
                                / ((MAX_ITER or MAX_ITER shl 7)).toFloat() * 0xFF).toInt()
                    )
                }
            }

            consumer.send(MandelbrotPoint(0f, 0f, 0, 0, 0))
        }

    fun CoroutineScope.accumulateConsumer(consumer: Channel<MandelbrotPoint>) =
        launch(Dispatchers.Default) {
            val x = consumer.receive()
            consumer.close()
        }



    fun draw(canvas: MandelbrotCanvas) {
        parallel(canvas)
//        for (y in 0 until canvas.height.toInt()) {
//            for (x in 0 until canvas.width.toInt()) {
//                drawMandelbrotPoint(x, canvas, y)
//            }
//        }
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

        canvas.drawPoint(
            x.toFloat(), y.toFloat(),
            (x.toFloat() / canvas.width * 0xFF).toInt(),
            (y.toFloat() / canvas.height * 0xFF).toInt(),
            ((iter or (iter shl 7)).toFloat()
                    / ((MAX_ITER or MAX_ITER shl 7)).toFloat() * 0xFF).toInt()
        )
    }

    interface MandelbrotCanvas {
        val height: Float
        val width: Float
        fun drawPoint(
            x: Float, y: Float,
            r: Int, g: Int, b: Int
        )
    }
}
