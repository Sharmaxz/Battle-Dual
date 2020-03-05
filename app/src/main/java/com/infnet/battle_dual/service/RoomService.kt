package com.infnet.battle_dual.service

import com.infnet.battle_dual.settings.AppPreferences
import khttp.*
import java.lang.Exception
import java.util.*

object RoomService {

    private const val url = "https://battle-dual.herokuapp.com/api/room/"

    fun get() : Any {
        val header = mapOf("Authorization" to "Bearer ${AppPreferences.env["BEARER"]}")

        val map = mutableMapOf<String, String>()
        var result = mutableMapOf<String, Any>()
        try {
            val response = get(url, headers=header)
            if(response.statusCode == 200) {
                response.text.replace("([{}\"\\[\\]])".toRegex(), "").split(",").forEach { it ->
                    val textSplited = it.split(":")
                    map[textSplited[0]] = textSplited[1]
                }

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
