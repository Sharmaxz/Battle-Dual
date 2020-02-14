package com.infnet.battle_dual.settings

import android.content.Context
import com.infnet.battle_dual.DotEnv

object AppPreferences  {
    var env : MutableMap<String, String> = mutableMapOf()

    fun init(context : Context) {
        val dotenv = DotEnv()
        env = dotenv.env
    }

    fun Bearer(bearer : String) {
        env["BEARER"] = bearer
    }

}
