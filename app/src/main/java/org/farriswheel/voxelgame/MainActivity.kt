package org.farriswheel.voxelgame

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.farriswheel.voxelgame.RustInterface.Companion.engineTick
import java.lang.Float.max
import java.lang.Float.min
import kotlin.concurrent.thread


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
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        surface = findViewById(R.id.surfaceView)
        surface.setEGLContextClientVersion(2)
        surface.setRenderer(VoxelRenderer(System.currentTimeMillis()))

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
                    RustInterface.lookAround(dx, dy)
                    touchStartX = event.x
                    touchStartY = event.y
                }
            }
            true
        }
        rendererSet = true


        val moveJoystick = findViewById<ImageView>(R.id.moveJoystick)
        moveJoystick.setOnTouchListener { v, event ->
            when(event.action) {
                1 -> RustInterface.stopMoving()
                else -> {
                    val dx = (event.x / moveJoystick.width) - 0.5f
                    val dz = (event.y / moveJoystick.height) - 0.5f
                    val a = invSqrt((dx * dx) + (dz * dz))
                    RustInterface.moveAround(dx * a, 0.0f, -dz * a)
                }
            }

            true
        }

        val jumpButton = findViewById<Button>(R.id.jumpButton)
        jumpButton.setOnTouchListener { v, event ->
            if(event.action == 0) {
                RustInterface.moveAround(0.0f, 1.5f, 0.0f)
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        if (rendererSet) {
            surface.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            surface.onResume()
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