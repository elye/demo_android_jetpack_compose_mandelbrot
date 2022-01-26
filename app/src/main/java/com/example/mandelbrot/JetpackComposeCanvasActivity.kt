package com.example.mandelbrot

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mandelbrot.drawing.ComposeMandelbrotCanvas


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
            drawMandelbrotObj.draw(ComposeMandelbrotCanvas(this))
        }
    }
}



