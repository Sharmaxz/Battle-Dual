package com.infnet.battle_dual

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val metrics = resources.displayMetrics
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_initial)
        arrowAnimation()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun arrowAnimation() {
        val view = findViewById<ConstraintLayout>(R.id.layout)
        val mainArrow = findViewById<ImageView>(R.id.arrow_up_main)

        val shakeAnim = AnimationUtils.loadAnimation(this, R.anim.arrow_shake)
        val detector = GestureDetector(this, OnSwipeTouchListener(this))

        view.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    mainArrow.startAnimation(shakeAnim)
                }
            }
            val swipe = detector. onTouchEvent(motionEvent)
            !swipe
        }

//        view.setOnTouchListener (object : OnTouchListener {
//            var diffY = motionEvent.getY() - downEvent.getY();
//            override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
//                var y = motionEvent.y;
//                var y_up = 0f;
//                when(motionEvent.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        mainArrow.startAnimation(shakeAnim)
//                        y_down = motionEvent.y;
//                    }
//                    MotionEvent.ACTION_UP -> {
//
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        println("Position Main Arrow: " + mainArrow.y + "y_dowm:" + y_down + "Result: " + (motionEvent.y - y_down)/2)
//                    }
//                    else -> {
//                        return false
//                    }
//                }
//                return true
//            }
//        })
    }
}

