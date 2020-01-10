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
import com.infnet.battle_dual.registration.fragments.LoginFragment
import com.infnet.battle_dual.registration.fragments.RegistrationFragment

import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_login_form.*

@SuppressLint("Registration")
class RegistrationActivity : AppCompatActivity() {

    private var fragmentManager = supportFragmentManager
    private var metrics : DisplayMetrics? = null
    private lateinit var arrow : Arrow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        metrics = resources.displayMetrics
        setContentView(R.layout.activity_registration)

        //Toolbar
        val toolbar = Toolbar(this, findViewById(R.id.toolbar), R.menu.menu)
        toolbar.titleEnabled(false)

        //Fragment Login
        login()
    }

    override fun onResume() {
        super.onResume()
        windowManager.defaultDisplay.getMetrics(metrics)
        arrow()

        //Fragment Registration
        btnRegistration.setOnClickListener { registration() }


    }

    override fun onBackPressed() {
        if(!arrow.login && !arrow.registration)
            super.onBackPressed()
        else
        {
            val screen = arrow.backPressed()
            if (screen) {
                login()
            }
        }
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
        val down_threshold = metrics?.heightPixels?.times(95)?.div(100)!!.toFloat()
        val up_threshold = metrics?.heightPixels?.times(40)?.div(100)!!.toFloat()

        arrow = Arrow(this, layout, arrow_main, rectangle, rectangle_form, up_threshold, down_threshold)
    }
    //endregion

    //region Activities
    private fun openCredits()  {
        intent = Intent(this, CreditsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
    //endregion

    //region Fragments

    private fun login() {
        val login = LoginFragment()
        fragmentManager.beginTransaction().replace(R.id.rectangle_form, login).commit()
    }

    private fun registration() {
        val registration = RegistrationFragment()
        fragmentManager.beginTransaction().replace(R.id.rectangle_form, registration).commit()
        registration.
        arrow.registration(true)
    }

    //endregion

}