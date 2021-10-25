package org.farriswheel.voxelgame

import android.opengl.GLES10.glClear
import android.opengl.GLES20.glClearColor
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT


class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("voxel")
        }
    }

    private inner class Renderer : GLSurfaceView.Renderer {
        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            debugString.text = rustGLSetup(gl!!)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            //clearColorGL()
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        }

        override fun onDrawFrame(gl: GL10?) {
            glClear(GL_COLOR_BUFFER_BIT)
            //drawFrameGL()
        }

    }

    external fun test(): String
    external fun rustGLSetup(gl: GL10) : String
    external fun drawFrameGL()
    external fun clearColorGL()

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
        surface.setRenderer(Renderer())
        rendererSet = true


    }

    /* fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityManager = getSystemService<Any>(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo: ConfigurationInfo = activityManager.deviceConfigurationInfo
        val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000 || isProbablyEmulator()
        if (supportsEs2) {
            glSurfaceView = GLSurfaceView(this)
            if (isProbablyEmulator()) {
                // Avoids crashes on startup with some emulator images.
                glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
            }
            glSurfaceView.setEGLContextClientVersion(2)
            glSurfaceView.setRenderer(RendererWrapper())
            rendererSet = true
            setContentView(glSurfaceView)
        } else {
            // Should never be seen in production, since the manifest filters
            // unsupported devices.
            Toast.makeText(
                this, "This device does not support OpenGL ES 2.0.",
                Toast.LENGTH_LONG
            ).show()
            return
        }
    }*/

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