package com.infnet.battle_dual

import android.os.Bundle
import android.view.Window
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.infnet.battle_dual.shared.DisplayMessage
import com.infnet.battle_dual.shared.SessionManager
import com.infnet.battle_dual.settings.AppPreferences
import com.infnet.battle_dual.creation.CreationActivity
import com.infnet.battle_dual.registration.RegistrationActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        AppPreferences.init(this)
        SessionManager.init(this)
        DisplayMessage.init(this)

        if(SessionManager.isLogged()) {
            val intent = Intent(this, CreationActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
       }
    }
}

