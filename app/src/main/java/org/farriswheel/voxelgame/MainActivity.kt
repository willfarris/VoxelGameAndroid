package org.farriswheel.voxelgame

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources


class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("voxel")
        }
    }

    private lateinit var surface: GLSurfaceView
    private lateinit var debugString: TextView
    private var rendererSet = false

    private fun invSqrt(x: Float): Float {
        var x = x
        val xhalf = 0.5f * x
        var i = java.lang.Float.floatToIntBits(x)
        i = 0x5f3759df - (i shr 1)
        x = java.lang.Float.intBitsToFloat(i)
        x *= 1.5f - xhalf * x * x
        return x
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        surface = findViewById(R.id.surfaceView)
        surface.setEGLContextClientVersion(3)
        surface.setRenderer(VoxelEngineRenderer())

        var touchStartX: Float? = null
        var touchStartY: Float? = null
        surface.setOnTouchListener { v, event ->
            when(event.action) {
                1 -> {
                    touchStartX = null
                    touchStartY = null
                }
                0 -> {
                    touchStartX = event.x
                    touchStartY = event.y
                }
                2 -> {
                    val dx = 0.002f * (event.x - touchStartX!!)
                    val dy = 0.002f  * (event.y - touchStartY!!)
                    VoxelEngine.lookAround(VoxelEngine.mEngine, dy, dx)
                    touchStartX = event.x
                    touchStartY = event.y
                }
            }
            true
        }
        rendererSet = true

        val moveJoystick = findViewById<ImageView>(R.id.moveJoystick)
        moveJoystick?.setOnTouchListener { v, event ->
            when(event.action) {
                1 -> VoxelEngine.stopMoving(VoxelEngine.mEngine)
                else -> {
                    val dx = (event.x / moveJoystick.width) - 0.5f
                    val dz = (event.y / moveJoystick.height) - 0.5f
                    val a = invSqrt((dx * dx) + (dz * dz))
                    VoxelEngine.moveAround(VoxelEngine.mEngine, dx * a, 0.0f, -dz * a)
                }
            }

            true
        }

        val jumpButton = findViewById<Button>(R.id.jumpButton)
        jumpButton?.setOnTouchListener { v, event ->
            if(event.action == 0) {
                VoxelEngine.playerJump(VoxelEngine.mEngine)
            }
            true
        }

        val breakButton = findViewById<Button>(R.id.breakButton)
        breakButton?.setOnClickListener {
            VoxelEngine.breakBlock(VoxelEngine.mEngine)
        }

        val placeButton = findViewById<Button>(R.id.placeButton)
        placeButton?.setOnClickListener {
            VoxelEngine.placeBlock(VoxelEngine.mEngine)
        }

        val pauseButton = findViewById<Button>(R.id.pauseButton)
        var isPaused = false
        pauseButton?.setOnClickListener {
            isPaused = if (isPaused) {
                VoxelEngine.resumeGame(VoxelEngine.mEngine)
                pauseButton.background = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_pause_circle_outline_24)
                false
            } else {
                VoxelEngine.pauseGame(VoxelEngine.mEngine)
                pauseButton.background = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_play_circle_outline_24)
                true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (rendererSet) {
            surface.onPause()
            VoxelEngine.pauseGame(VoxelEngine.mEngine)
        }
    }

    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            surface.onResume()
            VoxelEngine.resumeGame(VoxelEngine.mEngine)
        }
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


}