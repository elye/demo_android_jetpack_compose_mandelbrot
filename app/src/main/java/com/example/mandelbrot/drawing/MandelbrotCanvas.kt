package com.example.mandelbrot.drawing

interface MandelbrotCanvas {
        val height: Float
        val width: Float
        fun drawPoint(
            x: Float, y: Float,
            r: Int, g: Int, b: Int
        )
    }