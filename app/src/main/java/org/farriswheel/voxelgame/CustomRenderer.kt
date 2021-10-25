package org.farriswheel.voxelgame

import android.opengl.GLES10
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CustomRenderer : GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //clearColorGL()
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES10.glClear(GL10.GL_COLOR_BUFFER_BIT)
        //drawFrameGL()
    }

}