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

    private lateinit var surface: GLSurfaceView
    private lateinit var debugString: TextView
    private lateinit var voxelEngine: VoxelEngine
    private var rendererSet = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        voxelEngine = (this.application as VoxelGameApplication).mVoxelEngine

        surface = findViewById(R.id.surfaceView)
        surface.setEGLContextClientVersion(3)
        surface.setRenderer(voxelEngine)

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
                    voxelEngine.lookAround(dx, dy)
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
                1 -> voxelEngine.stopMoving()
                else -> {
                    val dx = (event.x / moveJoystick.width) - 0.5f
                    val dz = (event.y / moveJoystick.height) - 0.5f
                    val a = VoxelEngine.invSqrt((dx * dx) + (dz * dz))
                    voxelEngine.moveAround(dx * a, 0.0f, -dz * a)
                }
            }
            true
        }

        val jumpButton = findViewById<Button>(R.id.jumpButton)
        jumpButton?.setOnTouchListener { v, event ->
            if(event.action == 0) {
                voxelEngine.playerJump()
            }
            true
        }

        val breakButton = findViewById<Button>(R.id.breakButton)
        breakButton?.setOnClickListener {
            voxelEngine.breakBlock()
        }

        val placeButton = findViewById<Button>(R.id.placeButton)
        placeButton?.setOnClickListener {
            voxelEngine.placeBlock()
        }

        val pauseButton = findViewById<Button>(R.id.pauseButton)
        var isPaused = voxelEngine.isPaused()
        if (isPaused) {
            pauseButton?.background = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_pause_circle_outline_24)
        } else {
            pauseButton?.background = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_play_circle_outline_24)
        }
        pauseButton?.setOnClickListener {
            isPaused = if (isPaused) {
                voxelEngine.resumeGame()
                pauseButton.background = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_pause_circle_outline_24)
                false
            } else {
                voxelEngine.pauseGame()
                pauseButton.background = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_play_circle_outline_24)
                true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (rendererSet) {
            surface.onPause()
            //voxelEngine.pauseGame()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            surface.onResume()
            //voxelEngine.resumeGame()
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