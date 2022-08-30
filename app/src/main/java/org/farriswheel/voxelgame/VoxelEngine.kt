package org.farriswheel.voxelgame

import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

abstract class VoxelEngine {

    companion object {
        var mEngine: Long = 0

        /* Engine Functionality */
        @JvmStatic
        external fun initEngine(width: Int, height: Int, seed: Int, chunkRadius: Long): Long
        @JvmStatic
        external fun update(engine: Long, deltaTime: Float)
        @JvmStatic
        external fun pauseGame(engine: Long)
        @JvmStatic
        external fun resumeGame(engine: Long)

        @JvmStatic
        external fun test(): String


        /* Player controls */
        @JvmStatic
        external fun lookAround(engine: Long, dx: Float, dy: Float)
        @JvmStatic
        external fun moveAround(engine: Long, dx: Float, dy: Float, dz: Float)
        @JvmStatic
        external fun stopMoving(engine: Long)
        @JvmStatic
        external fun playerJump(engine: Long)
        @JvmStatic
        external fun breakBlock(engine: Long)
        @JvmStatic
        external fun placeBlock(engine: Long)
    }


}

class VoxelEngineRenderer() : GLSurfaceView.Renderer {
    companion object {
        private var startTime: Long = 0
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //TODO: Get a better way of setting width and height
        // Note: This are the screen dimensions of a Pixel 5a
        VoxelEngine.mEngine = VoxelEngine.initEngine(2400, 1080, 69, 5)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        startTime = System.nanoTime()
    }

    override fun onDrawFrame(gl: GL10?) {
        val deltaTime = (System.nanoTime()-startTime) / 1000000000.0f
        startTime = System.nanoTime()
        VoxelEngine.update(VoxelEngine.mEngine, deltaTime)
    }
}