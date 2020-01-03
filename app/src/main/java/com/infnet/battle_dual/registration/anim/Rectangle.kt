package com.infnet.battle_dual.registration.anim

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.View
import com.infnet.battle_dual.shared.DisplayManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope


class Rectangle (val context : Context,
                 private val rectangle : View,
                 private val content : View,
                 down_threshold : Float) {


    private val gravity : Float = 10f

    val activity : Activity = context as Activity
    val realMetrics = activity.windowManager.defaultDisplay.getRealMetrics(context.resources.displayMetrics)
    private val diff = context.resources.displayMetrics.heightPixels.minus(down_threshold)


    fun move(move : Float) {
        rectangle.y = move
    }

    fun follow(move : Float) {
        move(move.plus(diff))
    }

    fun alphaContent(alpha : Float) {
        content.alpha = alpha
    }


}