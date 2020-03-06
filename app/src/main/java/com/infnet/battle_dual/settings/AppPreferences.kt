package com.infnet.battle_dual.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.infnet.battle_dual.DotEnv
import com.infnet.battle_dual.shared.SessionManager

object AppPreferences  {

    private lateinit var context : Context

    private lateinit var prefs : SharedPreferences
    private lateinit var editor : Editor

    private const val PRIVATE_MODE = 0
    private const val PREF_NAME = "battle_dual-pref"

    var env : MutableMap<String, String> = mutableMapOf()

    fun init(context : Context) {
        this.context = context
        this.prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        this.editor = this.prefs.edit()
        if (getPreferences().count() == 0) {
            setPreferences()
        } else {
           env["CLIENT_SECRET_KEY"] = prefs.getString("CLIENT_SECRET_KEY", "").toString()
           env["CLIENT_PUBLIC_KEY"] = prefs.getString("CLIENT_PUBLIC_KEY", "").toString()
           env["BEARER"] = prefs.getString("BEARER", "").toString()
        }
    }

    fun bearer(bearer : String) {
        env["BEARER"] = bearer
        editor.putString("BEARER", bearer)
        editor.commit()
    }

    private fun getPreferences() : MutableMap<String, *> {
        return prefs.all
    }

    private fun setPreferences() {
        val dotenv = DotEnv()
        env = dotenv.env
        for (item in env) {
            editor.putString(item.key, item.value)
            editor.commit()
        }
    }
}
