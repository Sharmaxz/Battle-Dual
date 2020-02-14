package com.infnet.battle_dual.service

import com.infnet.battle_dual.settings.AppPreferences
import khttp.*

class UserService {

    val url = "http://127.0.0.1/api/user/"

    fun get() {
        print(AppPreferences.env)
        val r = get(url)

        }

    }
