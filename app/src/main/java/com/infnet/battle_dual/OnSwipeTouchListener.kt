package com.infnet.battle_dual

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

/**

 */

class OnSwipeTouchListener(var context : Context?) : GestureDetector.SimpleOnGestureListener() {

    private val THRESHOLD = 100
    private val VELOCITY_THRESHOLD = 100

    private var mainActivity = context as MainActivity

    override fun onFling(downEvent: MotionEvent, moveEvent: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        /**
         * It'll go calculate the difference between the touch event and down event.
         *
         */

        val diffY = moveEvent.y - downEvent.y
        val diffX = moveEvent.x - downEvent.x

        if (abs(diffY) > THRESHOLD && abs(velocityY) > VELOCITY_THRESHOLD) {
            if(diffY < 0) {
                /* Swipe Up */
            }
            else {
                /* Swipe Down */
            }
            return true
        }
        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        /**
         * This function is necessary to recognize the touch in a specific object and to call the onFling function.
         * The return always NEED to be false to work.
         */
        return false
    }
}