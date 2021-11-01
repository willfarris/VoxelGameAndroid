package org.farriswheel.voxelgame

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class VoxelEngine(private val startTime: Long) : GLSurfaceView.Renderer {
    companion object {
        @JvmStatic
        external fun test(): String
        @JvmStatic
        external fun lookAround(dx: Float, dy: Float)
        @JvmStatic
        external fun moveAround(dx: Float, dy: Float, dz: Float)
        @JvmStatic
        external fun stopMoving()
        @JvmStatic
        external fun tick(elapsed_time: Float)
        @JvmStatic
        external fun voxelOnSurfaceCreated(gl: GL10?, config: EGLConfig?, start_time: Long)
        @JvmStatic
        external fun voxelOnSurfaceChanged(gl: GL10?, width: Int, height: Int)
        @JvmStatic
        external fun voxelOnDrawFrame(gl: GL10?, elapsed_time: Float)

        val startTime = System.nanoTime()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        voxelOnSurfaceCreated(gl, config, startTime)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        voxelOnSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        voxelOnDrawFrame(gl, (System.currentTimeMillis() - startTime).toFloat() / 1000f)
    }
}