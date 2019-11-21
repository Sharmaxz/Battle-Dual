package com.infnet.battle_dual

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

open class DisplayMessage(private val context : Context?) {

    @SuppressLint("ShowToast")
    fun show(message : String, length : String = "short") {
        when (length){
            "short" -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            "long" -> Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("ShowToast")
    fun show(message : Float, length : String = "short") {
        val message  = message.toString()
        when (length){
            "short" -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            "long" -> Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}