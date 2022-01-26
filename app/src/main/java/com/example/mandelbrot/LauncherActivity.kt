package com.example.mandelbrot

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class LauncherActivity: BaseActivity() {
    @Composable
    override fun LoadComposable() {
        Launcher()
    }

    @Composable
    fun Launcher() {
        val context = LocalContext.current
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { context.StartActivityButton(SurfaceActivity::class.java) }
            item { context.StartActivityButton(JetpackComposeBitmapActivity::class.java) }
            item { context.StartActivityButton(JetpackComposeCanvasActivity::class.java) }
            item { context.StartActivityButton(SurfaceChannelActivity::class.java) }
            item { context.StartActivityButton(JetpackComposeBitmapChannelActivity::class.java) }
            item { context.StartActivityButton(JetpackComposeCanvasChannelActivity::class.java) }
            item { context.StartActivityButton(JetpackComposeBitmapChannelIncrementalActivity::class.java) }
        }
    }

    @Composable
    private fun <T : Activity> Context.StartActivityButton(clazz: Class<T>) {
        Button(onClick = { startActivity(Intent(this, clazz)) }) {
            Text(clazz.simpleName ?: "")
        }
    }
}

