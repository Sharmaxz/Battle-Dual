package com.infnet.battle_dual.registration

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.infnet.battle_dual.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationArrowAnim(context : Context,
                            private val view : ConstraintLayout,
                            private val arrow : ImageView,
                            private val up_threshold : Float,
                            private val down_threshold : Float) {

    private var hold = false
    private var y : Float? = null
    private var yDown : Float? = null
    private var yUp : Float? = null

    //Gravity Force
    private val gravity : Float = 10f

    private val shake = AnimationUtils.loadAnimation(context, R.anim.arrow_shake)
    private val gravity_soft = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_soft)
    private val gravity_normal = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_normal)
    //private val gravity_heavy = AnimationUtils.loadAnimation(context, R.anim.arrow_gravity_heavy)

    init {
        listener()
        update()
    }

    private fun update () {
        if(!hold)
            gravity()

        Thread.sleep(1)
        GlobalScope.launch {
            update()
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
                move()

           true
        }
    }


    private fun move () {
        val diffY = yDown!!.minus(y!!)

        var move : Float = down_threshold.minus(diffY)

        if(move < up_threshold)
            move = up_threshold
        else if (move > down_threshold)
            move = down_threshold

        arrow.y = move
    }

    private fun gravity() {
        val move = arrow.y + gravity

        if(move > gravity && move < down_threshold){
            arrow.y = move
        }
        else {
            arrow.y = down_threshold
            if (yUp != null &&  arrow.y == down_threshold) {
                println(down_threshold.minus(yUp!!))
                when(down_threshold.minus(yUp!!)) {
                    in 0f .. 500f  -> arrow.startAnimation(gravity_soft)
                    in 500f .. 1000f  -> arrow.startAnimation(gravity_normal)
                }
                yUp = null
            }
        }
    }
}