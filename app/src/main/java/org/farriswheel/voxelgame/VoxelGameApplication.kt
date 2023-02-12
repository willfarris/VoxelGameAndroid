package org.farriswheel.voxelgame

import android.app.Application
import android.util.Log
import kotlin.concurrent.thread

// Stores data for the game which persists across activities
class VoxelGameApplication: Application() {
    companion object {
        init {
            System.loadLibrary("voxel")
        }
    }
    val mVoxelEngine: VoxelEngine = VoxelEngine()

    init {
        mVoxelEngine.startTerrainThread()
        thread {
            while(true) {
                mVoxelEngine.update()
                Thread.sleep(8L)
            }
        }
    }
}
