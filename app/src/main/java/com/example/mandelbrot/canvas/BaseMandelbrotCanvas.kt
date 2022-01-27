package com.example.mandelbrot.canvas

import com.example.mandelbrot.drawing.MandelbrotCanvasCustom

interface BaseMandelbrotCanvas {
        val height: Float
        val width: Float
        fun drawPoint(
            x: Float, y: Float,
            r: Int, g: Int, b: Int
        )
        fun drawPoint(manPoint: MandelbrotCanvasCustom.MandelbrotPoint) {
            drawPoint(manPoint.x, manPoint.y, manPoint.r, manPoint.g, manPoint.b)
        }
    }