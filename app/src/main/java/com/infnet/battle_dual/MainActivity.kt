package com.infnet.battle_dual

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var displayMessage : DisplayMessage = DisplayMessage(this)
    private var metrics : DisplayMetrics? = null

    private var mainArrow : ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //px= dp * (dpi/160)
        metrics = resources.displayMetrics


        setContentView(R.layout.activity_initial)
        arrowAnimation()

    }

    @SuppressLint("ClickableViewAccessibility")
    suspend  fun arrowAnimation() {
        val view = findViewById<ConstraintLayout>(R.id.layout)
        mainArrow = findViewById(R.id.arrow_up_main)

        val shakeAnim = AnimationUtils.loadAnimation(this, R.anim.arrow_short_shake)


        var yDown : Float? = null
        var y : Float? = null
        var hold = false
        view.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    mainArrow?.startAnimation(shakeAnim)
                    yDown = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    hold = false
                    yDown  = null
                    y = null
                }
                MotionEvent.ACTION_MOVE -> {
                    hold = true
                    y = motionEvent.y
                }

            }
            if (hold && yDown != null) {
                swipe(mainArrow!!, y!!, yDown!!)
            }

            true
        }
    }

     fun swipe(mainArrow : ImageView, y : Float, yDown : Float) {
         var diffY = yDown - y
         val up_threshold = metrics?.heightPixels?.times(40)?.div(100)!!.toFloat()
         val down_threshold = metrics?.heightPixels?.times(95)?.div(100)!!.toFloat()
         val velocity_threshold = 80f

         if(diffY < 150f) {
             if (diffY >= velocity_threshold)
                 diffY = velocity_threshold
             else if (diffY <= velocity_threshold)
                 diffY = -velocity_threshold
         }
         else {

         }



         var move : Float = mainArrow.y - diffY


         if(move < up_threshold)
             move = up_threshold
         else if (move > down_threshold)
             move = down_threshold


         mainArrow.y = move
         //println("Arrow " + mainArrow.y.toString())
         //println(up_threshold.minus(mainArrow.y).div(up_threshold - down_threshold).times(100).toString())
         //mainArrow.alpha = up_threshold.minus(mainArrow.y).div(up_threshold - down_threshold)
         println(mainArrow.y.toString())

         println(mainArrow.y.toString())
     }

    suspend fun arrowGravity(mainArrow : ImageView, threshold : Float) {
        mainArrow.y += 0.2f
        GlobalScope.launch {
            if(mainArrow.y < threshold)
                arrowGravity(mainArrow, threshold)
        }
    }

}

