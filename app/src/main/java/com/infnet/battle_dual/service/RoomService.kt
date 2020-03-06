package com.infnet.battle_dual.service

import com.infnet.battle_dual.model.Room
import com.infnet.battle_dual.settings.AppPreferences
import khttp.*
import java.lang.Exception
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object RoomService {

    private const val url = "https://battle-dual.herokuapp.com/api/room/"

    fun get() : Any {
        val header = mapOf("Authorization" to "Bearer ${AppPreferences.env["BEARER"]}")

        val map = mutableMapOf<String, String>()
        var rooms = mutableListOf<Room>()
        var result = mutableMapOf<String, Any>()
        try {
            val response = get(url, headers=header)
            if(response.statusCode == 200) {
                val gson = Gson()
                val itemType = object : TypeToken<List<Room>>() {}.type
                val a = gson.fromJson<List<Room>>(response.text, itemType)
                println(a)
            }
            else {
                result = mutableMapOf(
                    "response" to response.statusCode,
                    "result" to response.text
                )
            }
        }
        catch (e: Exception) {
            result = mutableMapOf(
                "response" to 0
            )
        }

        return result
    }

}
