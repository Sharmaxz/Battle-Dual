package com.infnet.battle_dual.shared

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import com.infnet.battle_dual.model.User
import java.lang.Exception

open class SessionManager(context : Context) {

    private val gson = Gson()

    private val PRIVATE_MODE = 0
    private val PREF_NAME = "battle_dual-pref"

    private var prefs : SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    private var editor : Editor = prefs.edit();

    private lateinit var user : User

    fun create(nickname : String, email : String, first_name : String, last_name : String = "") {
        user = User(nickname, email, true, first_name, last_name)
        val userSerialized = gson.toJson(user)
        editor.putString("user", userSerialized)
        editor.commit()
        //gson.fromJson(userSerialized, User::class.java)
    }

    fun load() {

    }

    fun delete() {
        editor.clear()
        editor.commit()
    }

    fun isLogged() : Boolean {
        return try { user.active } catch (e: Exception) { false }
    }

}