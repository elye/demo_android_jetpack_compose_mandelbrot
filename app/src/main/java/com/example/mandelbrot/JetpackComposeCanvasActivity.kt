package com.example.mandelbrot

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mandelbrot.canvas.ComposeMandelbrotCanvas
import com.example.mandelbrot.drawing.MandelbrotCanvasCustom
import kotlin.system.measureTimeMillis


class JetpackComposeCanvasActivity: BaseActivity() {

    private val drawMandelbrotObj by lazy {
        MandelbrotCanvasCustom()
    }

    @Composable
    override fun LoadComposable() {
        DrawMandelbrot()
    }

    @Composable
    fun DrawMandelbrot() {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val elapsedTime= measureTimeMillis {
                drawMandelbrotObj.draw(ComposeMandelbrotCanvas(this))
            }
            Log.d("Measure", "JCCanvas took : ${elapsedTime}mS")
        }
    }
}



