package org.farriswheel.voxelgame

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("voxel")
        }
    }

    private lateinit var surface: GLSurfaceView
    private lateinit var debugString: TextView
    private var rendererSet = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        surface = GLSurfaceView(applicationContext)

        setContentView(surface)
        supportActionBar?.hide()

        surface.setEGLContextClientVersion(2)
        surface.setRenderer(VoxelRenderer())
        surface.setOnTouchListener { v, event ->
            Log.w("Touch", "${event.rawX}")
            RustInterface.setXY(event.rawX, event.rawY)
            true
        }
        rendererSet = true
    }

    override fun onPause() {
        super.onPause()
        if (rendererSet) {
            surface.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            surface.onResume()
        }
    }
}