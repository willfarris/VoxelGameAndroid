package org.farriswheel.voxelgame

import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class VoxelEngine : GLSurfaceView.Renderer {

    private var ptr: Long = 0
    private var startTime: Long = 0

    init {
        ptr = initEngineNative()
    }

    private companion object {

        @JvmStatic
        external fun initEngineNative(): Long
        @JvmStatic
        external fun initGLNative(ptr: Long, width: Int, height: Int)
        @JvmStatic
        external fun updateNative(ptr: Long, deltaTime: Float)
        @JvmStatic
        external fun drawFrameNative(ptr: Long)
        @JvmStatic
        external fun setColorNative(ptr: Long, red: Float, green: Float, blue: Float)
        @JvmStatic
        external fun resetGlResourcesNative(ptr: Long)

        @JvmStatic
        external fun pauseGameNative(ptr: Long)
        @JvmStatic
        external fun resumeGameNative(ptr: Long)
        @JvmStatic
        external fun isPausedNative(ptr: Long): Boolean

        /* Player controls */
        @JvmStatic
        external fun lookAroundNative(engine: Long, dx: Float, dy: Float)
        @JvmStatic
        external fun moveAroundNative(engine: Long, dx: Float, dy: Float, dz: Float)
        @JvmStatic
        external fun stopMovingNative(engine: Long)
        @JvmStatic
        external fun playerJumpNative(engine: Long)
        @JvmStatic
        external fun breakBlockNative(engine: Long)
        @JvmStatic
        external fun placeBlockNative(engine: Long)

    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {}

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        initGLNative(ptr, width, height)
        startTime = System.nanoTime()
    }

    fun update(deltaTime: Float) {
        updateNative(ptr, deltaTime)
    }

    override fun onDrawFrame(gl: GL10?) {
        //val deltaTime = (System.nanoTime() - startTime) / 1000000000.0f
        //startTime = System.nanoTime()
        //update(deltaTime)
        drawFrameNative(ptr)
    }

    fun resetGlResources() {
        resetGlResourcesNative(ptr)
    }

    fun stopMoving() {
        stopMovingNative(ptr)
    }

    fun moveAround(dx: Float, dy: Float, dz: Float) {
        moveAroundNative(ptr, dx, dy, dz)
    }

    fun playerJump() {
        playerJumpNative(ptr)
    }

    fun breakBlock() {
        breakBlockNative(ptr)
    }

    fun isPaused(): Boolean {
        return isPausedNative(ptr)
    }

    fun pauseGame() {
        pauseGameNative(ptr)
    }

    fun resumeGame() {
        resumeGameNative(ptr)
    }

    fun placeBlock() {
        placeBlockNative(ptr)
    }

    fun lookAround(dx: Float, dy: Float) {
        lookAroundNative(ptr, dx, dy)
    }
}