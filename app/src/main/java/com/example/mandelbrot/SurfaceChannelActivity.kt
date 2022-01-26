package com.example.mandelbrot

import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

class SurfaceChannelActivity : BaseActivity() {
    @Composable
    override fun LoadComposable() {
        SurfaceView()
    }

    @Composable
    fun SurfaceView() {
        AndroidView({ context ->
            LayoutInflater.from(context).inflate(
                R.layout.surfacelayoutchannel, FrameLayout(context), false
            )
        })
    }
}

