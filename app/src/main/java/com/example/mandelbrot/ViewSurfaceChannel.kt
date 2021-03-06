package com.example.mandelbrot

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.mandelbrot.drawing.MandelbrotCanvasCustomChannel
import com.example.mandelbrot.canvas.NativeMandelbrotCanvas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class ViewSurfaceChannel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var job: Job? = null
    private val drawMandelbrot by lazy {
        MandelbrotCanvasCustomChannel()
    }

    init {
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Do nothing for now
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        job?.cancel()
        job = null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        job = CoroutineScope(Dispatchers.Default).launch {
            synchronized(holder) {
                val elapsedTime= measureTimeMillis {
                    val canvas = holder.lockCanvas()
                    canvas?.let {
                        it.drawColor(Color.WHITE)
                        drawMandelbrot.draw(NativeMandelbrotCanvas(it))
                        holder.unlockCanvasAndPost(it)
                    }
                }
                Log.d("Measure",
                    "ViewSurfaceChannel took : ${elapsedTime}mS")

            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        setMeasuredDimension(
            View.resolveSize(desiredWidth, widthMeasureSpec),
            View.resolveSize(desiredHeight, heightMeasureSpec)
        )
    }
}
