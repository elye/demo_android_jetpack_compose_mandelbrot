package com.example.mandelbrot

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.example.mandelbrot.drawing.MandelbrotCanvasCustomChannel
import com.example.mandelbrot.canvas.NativeMandelbrotCanvas
import kotlin.system.measureTimeMillis


class JetpackComposeBitmapChannelActivity : BaseActivity() {
    private val drawMandelbrot by lazy {
        MandelbrotCanvasCustomChannel()
    }

    @Composable
    override fun LoadComposable() {
        DrawMandelbrotCanvas()
    }

    @Composable
    fun DrawMandelbrotCanvas() {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val elapsedTime= measureTimeMillis {
                val imageBitmap = Bitmap.createBitmap(
                    size.width.toInt(),
                    size.height.toInt(),
                    Bitmap.Config.ARGB_8888
                )
                val canvasBitmap = Canvas(imageBitmap)
                drawMandelbrot.draw(NativeMandelbrotCanvas(canvasBitmap))
                drawImage(imageBitmap.asImageBitmap())
            }
            Log.d("Measure", "JCBitmap took : ${elapsedTime}mS")
        }
    }
}
