package org.farriswheel.voxelgame

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

abstract class VoxelEngine {
    companion object {
        @JvmStatic
        external fun test(): String


        /* Player controls */
        @JvmStatic
        external fun lookAround(dx: Float, dy: Float)
        @JvmStatic
        external fun moveAround(dx: Float, dy: Float, dz: Float)
        @JvmStatic
        external fun stopMoving()


        /* Engine Functionality */
        @JvmStatic
        external fun initEngine(startTime: Float)
        @JvmStatic
        external fun tick(elapsedTime: Float)


        /* GL Functions */
        @JvmStatic
        external fun voxelOnSurfaceCreated(gl: GL10?, config: EGLConfig?)
        @JvmStatic
        external fun voxelOnSurfaceChanged(gl: GL10?, width: Int, height: Int)
        @JvmStatic
        external fun voxelOnDrawFrame(gl: GL10?, elapsed_time: Float)

        var startTime = System.nanoTime()
    }


}

class VoxelEngineRenderer(private val startTime: Long) : GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        VoxelEngine.voxelOnSurfaceCreated(gl, config)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        VoxelEngine.voxelOnSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        VoxelEngine.voxelOnDrawFrame(gl, (System.currentTimeMillis() - startTime).toFloat() / 1000f)
    }
}