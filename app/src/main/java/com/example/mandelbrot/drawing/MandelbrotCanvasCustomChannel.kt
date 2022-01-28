package com.example.mandelbrot.drawing

import android.util.Log
import com.example.mandelbrot.canvas.BaseMandelbrotCanvas
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlin.system.measureTimeMillis

open class MandelbrotCanvasCustomChannel : MandelbrotCanvasCustom() {

    open val CORE = 4

    private fun parallel(canvas: BaseMandelbrotCanvas, update: () -> Unit = {}) = runBlocking {
        fanoutFanin(canvas, update)
    }

    protected suspend fun CoroutineScope.fanoutFanin(
        canvas: BaseMandelbrotCanvas,
        update: () -> Unit = {}
    ) {
        val consumer = Channel<List<MandelbrotPoint>>()

        repeat(CORE) {
            computationProducer(
                consumer, canvas,
                canvas.height.toInt() * it / CORE,
                canvas.height.toInt() * (it + 1) / CORE
            )
        }
        drawConsumer(consumer, canvas, update).join()
    }

    private fun CoroutineScope.computationProducer(
        consumer: Channel<List<MandelbrotPoint>>,
        canvas: BaseMandelbrotCanvas,
        start: Int,
        end: Int
    ) = produce<Double>(Dispatchers.Default) {
        val mandelbrotPoints = mutableListOf<MandelbrotPoint>()
        val elapsedTime = measureTimeMillis {
            for (y in start until end) {
                for (x in 0 until canvas.width.toInt()) {
                    val iter = calculateIter(x, canvas, y)
                    mandelbrotPoints.add(createMandelbrotPoint(x, y, canvas, iter))
                }
            }
        }
        Log.d("Measure", "CoreProducer took : ${elapsedTime}mS ${Thread.currentThread()}")
        consumer.send(mandelbrotPoints)
    }

    private fun CoroutineScope.drawConsumer(
        consumer: Channel<List<MandelbrotPoint>>,
        canvas: BaseMandelbrotCanvas,
        update: () -> Unit = {}
    ) = launch {
        repeat(CORE) {
            val elapsedTime = measureTimeMillis {
                consumer.receive().forEach {
                    canvas.drawPoint(it.x, it.y, it.r, it.g, it.b)
                }
            }
            Log.d("Measure", "CoreConsumer took : ${elapsedTime}mS")
            update()
        }
        consumer.close()
    }

    override fun draw(canvas: BaseMandelbrotCanvas, update: () -> Unit) {
        parallel(canvas, update)
    }
}
