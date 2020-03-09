package com.infnet.battle_dual.shared

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import java.util.*

object DisplayMessage {

    private lateinit var context : Context

    fun init(context: Context) {
        this.context = context
    }

    @SuppressLint("ShowToast")
    fun show(message: String, length: String = "short") {
        when (length.toLowerCase(Locale.ROOT)) {
            "short" -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            "long" -> Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("ShowToast")
    fun show(message: Float, length: String = "short") {
        val message = message.toString()
        when (length.toLowerCase(Locale.ROOT)) {
            "short" -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            "long" -> Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
