package com.example.mandelbrot

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap


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
            val imageBitmap = Bitmap.createBitmap(
                size.width.toInt(),
                size.height.toInt(),
                Bitmap.Config.ARGB_8888
            )
            val canvasBitmap = Canvas(imageBitmap)
            drawMandelbrot.draw(NativeMandelbrotCanvas(canvasBitmap))
            drawImage(imageBitmap.asImageBitmap())
        }
    }
}
