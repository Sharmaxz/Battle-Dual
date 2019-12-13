package com.infnet.battle_dual.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.infnet.battle_dual.R

@SuppressLint("Registered")
class CreditsActivity : AppCompatActivity() {

    private var toolbar : Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        //Status Bar
        val window : Window = this.window
        window.statusBarColor = this.resources.getColor(R.color.background)

        toolbar()
    }


    //region Toolbar
    @SuppressLint("ResourceAsColor")
    fun toolbar() {
        toolbar = findViewById(R.id.toolbar)
        val back = this.resources.getDrawable(R.drawable.ic_chevron_left_white)
        back.setTint(R.color.background)
        toolbar?.navigationIcon = back
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onCreateOptionsMenu(menu : Menu): Boolean {
        super.onCreateOptionsMenu(menu);
        return true
    }
    //endregion
}



