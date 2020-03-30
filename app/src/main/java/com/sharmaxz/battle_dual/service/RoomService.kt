package com.sharmaxz.battle_dual.service

import com.sharmaxz.battle_dual.model.Room
import com.sharmaxz.battle_dual.settings.AppPreferences
import khttp.*
import java.lang.Exception
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sharmaxz.battle_dual.model.Hash


object RoomService {

    private const val url = "https://battle-dual.herokuapp.com/api/room/"

    fun get() : Any {
        val header = mapOf("Authorization" to "Bearer ${AppPreferences.env["BEARER"]}")
        var result: MutableMap<String, Any>

        try {
            val response = get(url, headers=header)
            if(response.statusCode == 200) {
                val gson = Gson()
                val itemType = object : TypeToken<List<Room>>() {}.type
                return gson.fromJson<List<Room>>(response.text, itemType)
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

    fun get(room : Int) : Any {
        val url = this.url + room.toString() + "/"
        val header = mapOf("Authorization" to "Bearer ${AppPreferences.env["BEARER"]}")
        var result: MutableMap<String, Any>

        try {
            val response = get(url, headers=header)
            if(response.statusCode == 200) {
                val gson = Gson()
                return gson.fromJson(response.text, Room::class.java)
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
