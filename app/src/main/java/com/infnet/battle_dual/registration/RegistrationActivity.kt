package com.infnet.battle_dual.registration

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.widget.ImageView
import com.infnet.battle_dual.R
import android.util.DisplayMetrics
import android.annotation.SuppressLint
import android.view.View
import com.infnet.battle_dual.shared.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.infnet.battle_dual.settings.CreditsActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.infnet.battle_dual.registration.anim.Arrow


@SuppressLint("Registration")
class RegistrationActivity : AppCompatActivity() {

    private var metrics : DisplayMetrics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        metrics = resources.displayMetrics
        setContentView(R.layout.activity_registration)

        //Toolbar
        val toolbar = Toolbar(this, findViewById(R.id.toolbar), R.menu.menu)
        toolbar.titleEnabled(false)

        //Arrow
        arrow()
    }

    //region Toolbar
    override fun onCreateOptionsMenu(menu : Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu ->  openCredits()
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region Arrow
    private fun arrow() {
        //96 navegation bar  metrics?.heightPixels?.minus(96)?.times(95)?.div(100)!!.toFloat()
        val view = findViewById<ConstraintLayout>(R.id.layout)
        val down_threshold = metrics?.heightPixels?.times(95)?.div(100)!!.toFloat()
        val up_threshold = metrics?.heightPixels?.times(40)?.div(100)!!.toFloat()
        val arrow = findViewById<ImageView>(R.id.arrow_main)
        val rectangle = findViewById<View>(R.id.rectangle)
//        val arrow0 = findViewById<ImageView>(R.id.arrow_0)
//        val arrow1 = findViewById<ImageView>(R.id.arrow_1)
//        val arrow2 = findViewById<ImageView>(R.id.arrow_2)

        Arrow(this, view, arrow, rectangle, up_threshold, down_threshold)

    }
    //endregion

    //region Activities
    private fun openCredits()  {
        intent = Intent(this, CreditsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
    //endregion

}