package com.infnet.battle_dual

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.infnet.battle_dual.registration.RegistrationActivity
import com.infnet.battle_dual.shared.DisplayManager
import com.infnet.battle_dual.shared.DisplayMessage
import com.infnet.battle_dual.shared.SessionManager


class MainActivity : AppCompatActivity() {

    private var displayMessage : DisplayMessage = DisplayMessage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val display = DisplayManager(this)
        val session = SessionManager(this)

        if(session.isLogged()) {
            println("Oh YAAAY!")
//            val intent = Intent(this, RegistrationActivity::class.java)
//            startActivity(intent)
            session.delete()
        }
        else {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
       }
    }

}

