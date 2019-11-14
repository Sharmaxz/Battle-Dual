package com.infnet.battle_dual

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs

class OnSwipeTouchListener(var context : Context?) : GestureDetector.SimpleOnGestureListener() {

    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100

    override fun onFling(downEvent: MotionEvent, moveEvent: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        val diffY = moveEvent.y - downEvent.y

        if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
            return diffY < 0
        }
        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }
}