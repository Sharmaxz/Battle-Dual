package com.infnet.battle_dual.registration

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
import com.infnet.battle_dual.shared.DisplayMessage
import com.infnet.battle_dual.R


@SuppressLint("Registered")
class RegistrationActivity : AppCompatActivity() {

    private var displayMessage : DisplayMessage = DisplayMessage(this)
    private var metrics : DisplayMetrics? = null

    private var mainArrow : ImageView? = null
    private var down_threshold : Float? = null
    private var hold = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        metrics = resources.displayMetrics

        setContentView(R.layout.activity_registration)

        down_threshold = metrics?.heightPixels?.times(95)?.div(100)!!.toFloat()
        mainArrow = findViewById(R.id.arrow_up_main)

        arrowAnimation()


        GlobalScope.launch {
            update()
        }

    }

    suspend fun update() {
        if(!hold)
            arrowGravity(mainArrow!!, down_threshold!!)

        Thread.sleep(1)
        GlobalScope.launch {
            update()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun arrowAnimation() {
        val view = findViewById<ConstraintLayout>(R.id.layout)
        mainArrow = findViewById(R.id.arrow_up_main)

        val shakeAnim = AnimationUtils.loadAnimation(this, R.anim.arrow_short_shake )


        var yDown : Float? = null
        var y : Float? = null
        val up_threshold = metrics?.heightPixels?.times(40)?.div(100)!!.toFloat()
        val down_threshold = metrics?.heightPixels?.times(95)?.div(100)!!.toFloat()

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
                swipe(mainArrow!!, y!!, yDown!!, up_threshold, down_threshold)
                //swipe(mainArrow!!, y!!, yDown!!, up_threshold, down_threshold, velocity_threshold)
            }

            true
        }
    }

    fun swipe(mainArrow : ImageView, y : Float, yDown : Float, up_threshold : Float,
              down_threshold : Float) {
        val diffY = yDown - y

        var move : Float = down_threshold - diffY

        if(move < up_threshold)
            move = up_threshold
        else if (move > down_threshold)
            move = down_threshold

        mainArrow.y = move
        //mainArrow.alpha = up_threshold.minus(mainArrow.y).div(up_threshold - down_threshold)
    }


    fun arrowGravity(mainArrow : ImageView, down_threshold : Float) {
        val move = mainArrow.y + 10f

        if(move < down_threshold)
            mainArrow.y = move
    }

}