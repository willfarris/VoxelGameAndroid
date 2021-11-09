package org.farriswheel.voxelgame

import android.opengl.GLSurfaceView
import android.util.Log
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
        external fun startEngine(startTime: Long)
        @JvmStatic
        external fun tick(elapsedTime: Long)
        @JvmStatic
        external fun breakBlock()


        /* GL Functions */
        @JvmStatic
        external fun voxelOnSurfaceCreated(gl: GL10?, config: EGLConfig?, startTimeMs: Long): String
        @JvmStatic
        external fun voxelOnSurfaceChanged(width: Int, height: Int)
        @JvmStatic
        external fun voxelOnDrawFrame(gl: GL10?, elapsedTime: Float)

        var startTime = System.nanoTime()
    }


}

class VoxelEngineRenderer : GLSurfaceView.Renderer {

    var isReady = false
    var startTimeMs = System.currentTimeMillis()

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        VoxelEngine.voxelOnSurfaceChanged(height, height)
        VoxelEngine.startEngine(System.currentTimeMillis())
        isReady = true
    }

    override fun onDrawFrame(gl: GL10?) {
        val time = (System.currentTimeMillis() - startTimeMs).toDouble() / 1000f
        //Log.w("ENGINE TIME", "$time")
        VoxelEngine.voxelOnDrawFrame(gl, time.toFloat())
    }
}