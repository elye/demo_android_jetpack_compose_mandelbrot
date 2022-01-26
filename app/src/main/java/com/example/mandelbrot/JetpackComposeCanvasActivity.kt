package com.example.mandelbrot

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode


class JetpackComposeCanvasActivity: BaseActivity() {
    companion object {
        private const val MAX_ITER = 300
        private const val ZOOM = 300
    }

    @Composable
    override fun LoadComposable() {
        DrawMandelbrot()
    }

    @Composable
    fun DrawMandelbrot() {

        Canvas(modifier = Modifier.fillMaxSize()) {
            for (y in 700 until 900) {
                for (x in 0 until size.width.toInt()) {
                    var zx = 0.0
                    var zy = 0.0
                    val cX = (x - size.width / 2) / ZOOM
                    val cY = (y - size.height / 2) / ZOOM
                    var iter = MAX_ITER
                    while (zx * zx + zy * zy < 4.0 && iter > 0) {
                        val tmp = zx * zx - zy * zy + cX
                        zy = 2.0 * zx * zy + cY
                        zx = tmp
                        iter--
                    }

                    drawPoints(
                        listOf(Offset(x.toFloat(), y.toFloat())),
                        PointMode.Points,
                        Color(
                            ((x/size.width) * 0xFF).toInt(),
                            ((y/size.height) * 0xFF).toInt(),
                            ((iter or (iter shl 7)).toFloat()
                                    /((MAX_ITER or MAX_ITER shl 7)) * 0xFF).toInt()
                        ),
                        1f
                    )
                }
            }
        }
    }
}


