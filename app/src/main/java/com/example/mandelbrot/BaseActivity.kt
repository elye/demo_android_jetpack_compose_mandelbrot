package com.example.mandelbrot

import android.os.Bundle
import androidx.compose.material.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.mandelbrot.ui.theme.MandelbrotTheme

abstract class BaseActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MandelbrotTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoadComposable()
                }
            }
        }
    }

    @Composable
    abstract fun LoadComposable()
}
