package org.farriswheel.voxelgame

import android.opengl.GLES10
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class VoxelRenderer : GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        RustInterface.onSurfaceCreated()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        RustInterface.onSurfaceChanged()
    }

    override fun onDrawFrame(gl: GL10?) {
        RustInterface.onDrawFrame()
    }

}