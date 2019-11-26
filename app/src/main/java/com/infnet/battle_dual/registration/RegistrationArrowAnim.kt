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

class RegistrationArrowAnim(private val context : Context,
                            private val view : ConstraintLayout,
                            private val arrow : ImageView,
                            private val up_threshold : Float,
                            private val down_threshold : Float) {

    private var hold = false
    private var y : Float? = null
    private var yDown : Float? = null

    private val shake = AnimationUtils.loadAnimation(context, R.anim.arrow_short_shake)

    init {
        listener()
        update()
    }

    private fun update () {
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
                    yDown  = null
                    y = null
                }
                MotionEvent.ACTION_MOVE -> {
                    hold = true
                    y = event.y
                }

            }

             if (hold && yDown != null)
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
        val move = arrow.y + 10f

        if(move < down_threshold)
            arrow.y = move
    }
}