package com.example.mandelbrot

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.mandelbrot.canvas.BitmapCanvasMandelbrotCanvas
import com.example.mandelbrot.drawing.MandelbrotCanvasCustomChannelIncremental
import kotlin.system.measureTimeMillis

class JetpackComposeBitmapCanvasChannelIncrementalActivity : BaseActivity() {
    private val drawMandelbrot by lazy {
        MandelbrotCanvasCustomChannelIncremental()
    }

    @Composable
    override fun LoadComposable() {
        DrawMandelbrotCanvas()
    }

    @Composable
    fun DrawMandelbrotCanvas() {
        var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }
        var width = 0
        var height = 0

        Canvas(modifier = Modifier.fillMaxSize()) {
            val elapsedTime= measureTimeMillis {
                width = size.width.toInt()
                height = size.height.toInt()
                imageBitmap?.let {
                    drawImage(it)
                }
            }
            Log.d("Measure", "JCBitmap took : ${elapsedTime}mS")
        }

        LaunchedEffect(key1 = Unit) {
            val canvas = BitmapCanvasMandelbrotCanvas(width, height)
            drawMandelbrot.draw(canvas) {
                imageBitmap = canvas.bitmap.asImageBitmap()
            }
        }
    }
}
