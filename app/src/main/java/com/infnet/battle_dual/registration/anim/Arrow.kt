package com.infnet.battle_dual.registration.anim

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.infnet.battle_dual.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 *  Registration Arrow Animation
 *
 *  This code have responsibility to do the animation of the Registration Screen.
 *  Here is possible to change the velocity that arrow fall and other preferences
 *  as change the range of the kick animation.
 *
 *  JUST USE THIS CODE WITH THE RegistrationActivity!
 *
 *  You need to get the view and metrics of the screen.
 *
 *  Usage:
 *
 *   val view = findViewById<ConstraintLayout>(R.id.layout)
 *   val down_threshold = metrics?.heightPixels?.times(95)?.div(100)!!.toFloat()
 *   val up_threshold = metrics?.heightPixels?.times(40)?.div(100)!!.toFloat()
 *
 *   Arrow(this, view, arrow, up_threshold, down_threshold)
 *
 **/

class Arrow(context : Context,
            private val view : ConstraintLayout,
            private val arrow : ImageView,
            rectangle : View,
            private val up_threshold : Float,
            private val down_threshold : Float) {

    //Preferences
    //Gravity Force
    private val gravity : Float = 10f

    private var hold = false
    private var y : Float? = null
    private var yUp : Float? = null
    private var yDown : Float? = null

    //Rectangle
    private val rectangleAnim = Rectangle(context, rectangle, down_threshold)


    private val shake = AnimationUtils.loadAnimation(context, R.anim.arrow_shake)
    private val blink = AnimationUtils.loadAnimation(context, R.anim.arrow_blink)
    private val blink1 = AnimationUtils.loadAnimation(context, R.anim.arrow_blink1)
    private val blink2 = AnimationUtils.loadAnimation(context, R.anim.arrow_blink2)
    private val gravity_soft = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_soft)
    private val gravity_normal = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_normal)
    private val gravity_heavy = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_heavy)

    init {
        listener()
        update()
    }

    private fun update () {
        if(!hold)
            gravity()

        Thread.sleep(1)
        GlobalScope.launch {
            supervisorScope {
                update()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listener() {
        view.setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    arrow.startAnimation(shake)
                    yDown = event.y
                }
                MotionEvent.ACTION_UP -> {
                    hold = false
                    yUp = event.y
                    yDown  = null
                    y = null
                }
                MotionEvent.ACTION_MOVE -> {
                    hold = true
                    y = event.y
                }
            }
             if (yDown != null && hold)
                 slide()

           true
        }
    }

    private fun slide () {
        val diffY = yDown!!.minus(y!!)
        var move : Float = down_threshold.minus(diffY)

        if(move < up_threshold)
            move = up_threshold
        else if (move > down_threshold)
            move = down_threshold

        move(move)
    }

    private fun gravity() {
        val move = arrow.y + gravity

        if(move > gravity && move < down_threshold)
            move(move)
        else {
            move(down_threshold)
            if (yUp != null &&  arrow.y == down_threshold) {
                    when(down_threshold.minus(yUp!!)) {
                        in 0f .. 500f  -> arrow.startAnimation(gravity_soft)
                        else  -> arrow.startAnimation(gravity_normal)
                    }
                yUp = null
            }
        }
    }

    fun move(move : Float) {
        arrow.alpha = up_threshold.minus(arrow.y).div(up_threshold - down_threshold)
        arrow.y = move

        rectangleAnim.follow(move)
    }

//    fun slide () {
//        Thread(Runnable {
//            Thread.sleep(500)
//            arrow0.startAnimation(blink)
//            Thread.sleep(500)
//            arrow1.startAnimation(blink1)
//            Thread.sleep(500)
//            arrow2.startAnimation(blink2)
//        })
//
//        GlobalScope.launch {
//            slide()
//        }
//   }
}