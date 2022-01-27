package com.example.mandelbrot

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.mandelbrot.drawing.MandelbrotCanvasCustomChannelIncremental
import com.example.mandelbrot.canvas.NativeMandelbrotCanvas
import kotlin.system.measureTimeMillis

class JetpackComposeBitmapChannelIncrementalActivity : BaseActivity() {
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
            val bitmap = Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888
            )
            val canvasBitmap = Canvas(bitmap)
            drawMandelbrot.draw(NativeMandelbrotCanvas(canvasBitmap)) {
                imageBitmap = bitmap.asImageBitmap()
            }
        }
    }
}