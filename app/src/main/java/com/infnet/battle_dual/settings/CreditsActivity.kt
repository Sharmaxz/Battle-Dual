package com.infnet.battle_dual.settings

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.infnet.battle_dual.R
import com.infnet.battle_dual.shared.DisplayMessage

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

    //region Essentials

    fun openBrowser(url : String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            startActivity(intent)
        } catch (e: ActivityNotFoundException) { // Chrome is not installed
            DisplayMessage.show("Browser not found", "long")
            intent.setPackage(null);
        }
    }

    //endregion Essentials

    //region Buttons

    fun shadow(v : View){
        openBrowser("https://www.linkedin.com/in/joao-psilva/")
    }

    fun elian (v : View){
        openBrowser("https://www.linkedin.com/in/elian-pinheiro/")
    }

    fun iasmim(v : View){
        openBrowser("https://www.linkedin.com/in/iasmim-de-jesus-silveira-303924130/")
    }

    fun jonh(v : View){
        openBrowser("https://www.linkedin.com/in/visualpy/")
    }

    fun thiago(v : View){

    }

    fun infnet(v : View){
        openBrowser("https://www.infnet.edu.br/infnet/")
    }

    //endregion Buttons


}



