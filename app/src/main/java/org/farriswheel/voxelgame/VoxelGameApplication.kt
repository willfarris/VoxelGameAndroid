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
        thread {
            var startTime = System.nanoTime()
            while(true) {
                val deltaTime = (System.nanoTime() - startTime) / 1000000000.0f
                startTime = System.nanoTime()
                mVoxelEngine.update(deltaTime)
                Thread.sleep(8L)
            }
        }
    }
}