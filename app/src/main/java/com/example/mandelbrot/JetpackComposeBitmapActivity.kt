package com.example.mandelbrot

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.example.mandelbrot.canvas.BitmapCanvasMandelbrotCanvas
import com.example.mandelbrot.drawing.MandelbrotCanvasCustom
import com.example.mandelbrot.canvas.NativeMandelbrotCanvas
import kotlin.system.measureTimeMillis


class JetpackComposeBitmapActivity : BaseActivity() {
    private val drawMandelbrot by lazy {
        MandelbrotCanvasCustom()
    }

    @Composable
    override fun LoadComposable() {
        DrawMandelbrotCanvas()
    }

    @Composable
    fun DrawMandelbrotCanvas() {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val elapsedTime= measureTimeMillis {
                val canvas = BitmapCanvasMandelbrotCanvas(
                    size.width.toInt(), size.height.toInt()
                )
                drawMandelbrot.draw(canvas)
                drawImage(canvas.bitmap.asImageBitmap())
            }
            Log.d("Measure", "JCBitmap took : ${elapsedTime}mS")
        }
    }
}
