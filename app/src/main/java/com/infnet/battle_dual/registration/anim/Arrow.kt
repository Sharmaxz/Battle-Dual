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
 *   val registration_up_threshold = resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"))
 *
 *   Arrow(this, view, arrow, up_threshold, down_threshold, registration_up_threshold)
 *
 **/

class Arrow(private val context : Context,
            private val view : ConstraintLayout,
            private val arrow : ImageView,
            private val rectangle : View,
            private val content : View,
            private val up_threshold : Float,
            private val down_threshold : Float,
            private val registration_up_threshold : Int) {

    //Preferences
    // region Gravity Force
    private val gravity_force : Float = 10f
    private val limit : Float = 1.6f
    // endregion

    private var hold = false
    private var gravity = true
    var login = false
    var registration = false

    private var y : Float? = null
    private var yUp : Float? = null
    private var yDown : Float? = null

    // region Rectangle
    private val rectangleAnim = Rectangle(context, rectangle, content, down_threshold)
    // endregion

    private val shake = AnimationUtils.loadAnimation(context, R.anim.arrow_shake)
    private val blink = AnimationUtils.loadAnimation(context, R.anim.arrow_blink)
    private val blink1 = AnimationUtils.loadAnimation(context, R.anim.arrow_blink1)
    private val blink2 = AnimationUtils.loadAnimation(context, R.anim.arrow_blink2)
    private val gravity_soft = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_soft)
    private val gravity_normal = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_normal)

    init {
        listener()
        update()
    }

    private fun update () {

        if(registration)
            slideUp(1f)
        else if(login && !registration) {
            slideDown(limit)
        }
        else if(!login)
            slideUp(limit)

        if(!hold && gravity)
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

                    if(login && !registration && yDown!! < rectangle.y)
                        gravity = true
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
             if (yDown != null && hold) {
                 swipe()
             }
           true
        }
    }

    private fun move(move : Float) {
        val percentage = percentage(up_threshold)
        arrow.alpha = percentage
        arrow.y = move
        rectangleAnim.alphaContent(1 - percentage )
        rectangleAnim.follow(move)
    }

    private fun swipe(direction : String = "UP") {
        val diffY = yDown!!.minus(y!!)
        var move = 0f
        when(direction) {
            "UP" ->  move = down_threshold.minus(diffY)
            "DOWN" -> move  = up_threshold.minus(diffY)
        }

        if(move < up_threshold) {
            move = up_threshold
            if(hold) {
                gravity = false
                login = true
            }
        }
        else if (move > down_threshold)
            move = down_threshold

        move(move)
    }

    private fun slide(limit : Float) : Boolean {
        return arrow.y > limit && arrow.y < down_threshold
    }

    private fun percentage(limit: Float) : Float {
        return limit.minus(arrow.y).div(limit - down_threshold)
    }

    private fun slideUp(limit : Float = 0f) {
        if (registration) {
            if (slide(registration_up_threshold - rectangle.y)) {
                val percentage = percentage(registration_up_threshold.toFloat())
                if (percentage < limit) {
                    val move = arrow.y - gravity_force
                    if (rectangle.y > registration_up_threshold) {
                        move(move)
                        gravity = false
                    } else if (move <= registration_up_threshold) {
                        move(registration_up_threshold.toFloat())
                    }
                }
            }
        }
        else if (!hold && !login) {
            if (slide(up_threshold)) {
                val percentage =  percentage(up_threshold)

                if (percentage < limit.minus(1f)) {
                    val move = arrow.y - gravity_force
                    if (move > up_threshold) {
                        move(move)
                        gravity = false
                    } else if (move <= up_threshold) {
                        move(up_threshold)
                        login = true
                    }
                }
            }
        }
    }

    private fun slideDown(limit : Float = 0f) {
        val percentage =  percentage(up_threshold)

        if (percentage < limit.minus(1f)) {
            val move = arrow.y + gravity_force
            if (move < up_threshold) {
                move(move)
            } else if (move <= up_threshold) {
                move(up_threshold)
                gravity = false
            }
        }

    }

    private fun gravity() {
        if(arrow.y != 0f && arrow.y < down_threshold) {
            val move = arrow.y + gravity_force
            if (move > gravity_force && move < down_threshold)
                move(move)
            else {
                move(down_threshold)
                login = false
                if (yUp != null && arrow.y == down_threshold) {
                    when (down_threshold.minus(yUp!!)) {
                        in 0f..500f -> arrow.startAnimation(gravity_soft)
                        else -> arrow.startAnimation(gravity_normal)
                    }
                    yUp = null
                }
            }
        }
    }

    fun registration() {
            login = false
            registration = true
    }

    fun backPressed()  {
        if (registration) {
            registration = false
            login = true
        }
    }

}