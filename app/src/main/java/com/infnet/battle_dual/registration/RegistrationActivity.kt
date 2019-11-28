package com.infnet.battle_dual.registration

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.infnet.battle_dual.shared.DisplayMessage
import com.infnet.battle_dual.R


@SuppressLint("Registration")
class RegistrationActivity : AppCompatActivity() {

    private var metrics : DisplayMetrics? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        metrics = resources.displayMetrics

        setContentView(R.layout.activity_registration)

        arrow()

    }

    fun arrow() {
        val view = findViewById<ConstraintLayout>(R.id.layout)
        val down_threshold = metrics?.heightPixels?.times(95)?.div(100)!!.toFloat()
        val up_threshold = metrics?.heightPixels?.times(40)?.div(100)!!.toFloat()
        val arrow = findViewById<ImageView>(R.id.arrow_up_main)
        RegistrationArrowAnim(this, view, arrow, up_threshold, down_threshold)
    }

}