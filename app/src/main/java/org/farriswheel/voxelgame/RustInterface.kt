package org.farriswheel.voxelgame

import javax.microedition.khronos.opengles.GL10

class RustInterface {
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
        external fun engineTick()

        @JvmStatic
        external fun onSurfaceCreated()

        @JvmStatic
        external fun onSurfaceChanged()

        @JvmStatic
        external fun onDrawFrame()
    }
}