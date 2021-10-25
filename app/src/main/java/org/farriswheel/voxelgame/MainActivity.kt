package org.farriswheel.voxelgame

import android.opengl.GLES10.glClear
import android.opengl.GLES20.glClearColor
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.farriswheel.voxelgame.RustInterface.Companion.test
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT


class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("voxel")
        }
    }

    private lateinit var surface: GLSurfaceView
    private lateinit var debugString: TextView
    private var rendererSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        debugString = findViewById<TextView>(R.id.rustTextView).apply {
            text = test()
        }

        surface = findViewById(R.id.surfaceView)
        surface.setEGLContextClientVersion(2)
        surface.setRenderer(CustomRenderer())
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