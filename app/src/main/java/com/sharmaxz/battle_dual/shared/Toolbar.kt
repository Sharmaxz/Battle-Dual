package com.sharmaxz.battle_dual.shared

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.view.Menu
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.sharmaxz.battle_dual.registration.RegistrationActivity
import com.sharmaxz.battle_dual.R
import kotlinx.android.synthetic.main.activity_registration.*


class Toolbar(val context : Context,
              val toolbar : Toolbar,
              val inflate : Int)  {

    private val appCompatActivity = (context as AppCompatActivity)

    init {
        appCompatActivity.setSupportActionBar(toolbar)
        toolbar.inflateMenu(inflate)
    }

    fun titleEnabled(active : Boolean) {
        if (active)
            appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(true)
        else
            appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    fun title(title : String) {
        if (title != "") {
            titleEnabled(true)
            appCompatActivity.supportActionBar?.title = title
        }
    }

    @SuppressLint("ResourceAsColor")
    fun titleColor(color : Int) {
        toolbar.setTitleTextColor(ContextCompat.getColor(context, color))
    }
}
