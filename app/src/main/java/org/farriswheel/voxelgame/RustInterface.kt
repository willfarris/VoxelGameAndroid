package org.farriswheel.voxelgame

import javax.microedition.khronos.opengles.GL10

class RustInterface {
    companion object {
        @JvmStatic
        external fun test(): String

        @JvmStatic
        external fun rustGLSetup(gl: GL10) : String

        @JvmStatic
        external fun drawFrameGL()

        @JvmStatic
        external fun clearColorGL()
    }
}