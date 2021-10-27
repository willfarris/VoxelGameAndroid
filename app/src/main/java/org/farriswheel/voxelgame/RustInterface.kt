package org.farriswheel.voxelgame

import javax.microedition.khronos.opengles.GL10

class RustInterface {
    companion object {

        @JvmStatic
        external fun test(): String

        @JvmStatic
        external fun setXY(nx: Float, ny: Float)

        @JvmStatic
        external fun onSurfaceCreated()

        @JvmStatic
        external fun onSurfaceChanged()

        @JvmStatic
        external fun onDrawFrame()
    }
}