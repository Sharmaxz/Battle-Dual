package com.infnet.battle_dual.registration.anim

import android.app.Activity
import android.content.Context
import android.view.View
import com.infnet.battle_dual.shared.DisplayManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Rectangle (val context : Context,
                 private val rectangle : View,
                 down_threshold : Float) {

    val activity : Activity = context as Activity
    val realMetrics = activity.windowManager.defaultDisplay.getRealMetrics(context.resources.displayMetrics)
    private val diff = context.resources.displayMetrics.heightPixels.minus(down_threshold)


    fun move(move : Float) {
        rectangle.y = move
        //slideUp()
    }

    fun follow(move : Float) {
        move(move.plus(diff))
        println("${rectangle.y} max: ${context.resources.displayMetrics.heightPixels}")
    }

    fun slideUp(limit : Float = 0f) {
        val move = rectangle.y - 10f

        if(move > limit) {
            move(move)
            Thread.sleep(1)
            GlobalScope.launch {
                slideUp(limit)
            }
        }
    }

    fun slideDown() {

    }

}