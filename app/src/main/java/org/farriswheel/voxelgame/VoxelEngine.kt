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
        @JvmStatic
        external fun playerJump()


        /* Engine Functionality */
        @JvmStatic
        external fun initEngine()
        @JvmStatic
        external fun tick(elapsedTime: Float)
        @JvmStatic
        external fun breakBlock()


        /* GL Functions */
        @JvmStatic
        external fun voxelOnSurfaceCreated(gl: GL10?, config: EGLConfig?, seed: Int, world_radius: Int): String
        @JvmStatic
        external fun voxelOnSurfaceChanged(gl: GL10?, width: Int, height: Int)
        @JvmStatic
        external fun voxelOnDrawFrame(gl: GL10?, elapsedTime: Float)

        var startTime = System.nanoTime()
    }


}

class VoxelEngineRenderer(private val startTimeMs: Long) : GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.w("Engine: ", VoxelEngine.voxelOnSurfaceCreated(gl, config, 4, 4))
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //VoxelEngine.voxelOnSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        val time = (System.currentTimeMillis() - startTimeMs).toDouble() / 1000f
        //Log.w("ENGINE TIME", "$time")
        VoxelEngine.voxelOnDrawFrame(gl, time.toFloat())
    }
}