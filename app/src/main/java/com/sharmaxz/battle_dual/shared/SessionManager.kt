package com.sharmaxz.battle_dual.shared

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import com.sharmaxz.battle_dual.DotEnv
import com.sharmaxz.battle_dual.model.User
import com.sharmaxz.battle_dual.settings.AppPreferences
import java.lang.Exception

object SessionManager {

    private val gson = Gson()

    private lateinit var context : Context

    private lateinit var prefs : SharedPreferences
    private lateinit var editor : Editor

    private const val PRIVATE_MODE = 0
    private const val PREF_NAME = "session-pref"

    lateinit var user : User

    fun init(context : Context) {
        this.context = context
        this.prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        this.editor = this.prefs.edit()
        var user = prefs.getString("user", "")
        if (user != "") {
            this.user = gson.fromJson(user, User::class.java)
        }
    }

    fun create(user : User) {
        this.user = user
        val userSerialized = gson.toJson(user)
        editor.putString("user", userSerialized)
        editor.commit()
    }

    fun update(attribute : Any, value : Any) {
//        val userSerialized = gson.toJson(user)
//        editor.putString("user", userSerialized)
//        editor.commit()
        //gson.fromJson(userSerialized, User::class.java)
    }

    private fun getPreferences() : MutableMap<String, *> {
        return prefs.all
    }

    fun isLogged() : Boolean {
        if (getPreferences().isEmpty()) {
            return false
        }
        return true
    }

    fun delete() {
        editor.clear()
        editor.commit()
    }

}
