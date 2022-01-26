package com.example.mandelbrot.drawing

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class MandelbrotCanvasCustomChannel {

    companion object {
        private const val MAX_ITER = 300
        private const val ZOOM = 300
        private val CORE = 5
    }

    data class MandelbrotPoint(
        val x: Float, val y: Float,
        val r: Int, val g: Int, val b: Int
    )

    private fun parallel(canvas: MandelbrotCanvas) = runBlocking {
        val consumer = Channel<List<MandelbrotPoint>>()

        repeat(CORE) {
            accumulateProducer(
                consumer, canvas,
                canvas.height.toInt() * it / CORE,
                canvas.height.toInt() * (it + 1) / CORE
            )
        }
        accumulateConsumer(consumer, canvas).join()
    }

    fun CoroutineScope.accumulateProducer(
        consumer: Channel<List<MandelbrotPoint>>,
        canvas: MandelbrotCanvas,
        start: Int,
        end: Int
    ) = produce<Double>(Dispatchers.Default) {
        val mandelbrotPoints = mutableListOf<MandelbrotPoint>()
        for (y in start until end) {
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

                mandelbrotPoints.add(
                    MandelbrotPoint(
                        x.toFloat(), y.toFloat(),
                        (x.toFloat() / canvas.width * 0xFF).toInt(),
                        (y.toFloat() / canvas.height * 0xFF).toInt(),
                        ((iter or (iter shl 7)).toFloat()
                                / ((MAX_ITER or MAX_ITER shl 7)).toFloat() * 0xFF).toInt()
                    )
                )
            }
        }
        consumer.send(mandelbrotPoints)
    }

    fun CoroutineScope.accumulateConsumer(
        consumer: Channel<List<MandelbrotPoint>>,
        canvas: MandelbrotCanvas
    ) =
        launch {
            repeat(CORE) {
                val elapsedTime = measureTimeMillis {
                    consumer.receive().forEach {
                        canvas.drawPoint(it.x, it.y, it.r, it.g, it.b)
                    }
                }
                Log.d("Measure", "CoreConsumer took : ${elapsedTime}mS")

            }
            consumer.close()
        }

    fun draw(canvas: MandelbrotCanvas) {
        parallel(canvas)
    }
}
