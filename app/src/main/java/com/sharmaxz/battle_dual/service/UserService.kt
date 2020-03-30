package com.sharmaxz.battle_dual.service

import com.sharmaxz.battle_dual.model.User
import com.sharmaxz.battle_dual.settings.AppPreferences
import khttp.*
import java.lang.Exception
import java.util.*


object UserService {

    private const val url = "https://battle-dual.herokuapp.com/api/user/"
    private const val url_search = "https://battle-dual.herokuapp.com/api/user/?search="

    fun get(nickname : String = "") : Any {
        var url = this.url
        val header = mapOf("Authorization" to "Bearer ${AppPreferences.env["BEARER"]}")

        if(nickname != "") {
            url = url_search + nickname.toLowerCase(Locale.ENGLISH)
        }

        val map = mutableMapOf<String, String>()
        var result = mutableMapOf<String, Any>()
        try {
            val response = get(url, headers=header)
            if(response.statusCode == 200) {
                response.text.replace("([{}\"\\[\\]])".toRegex(), "").split(",").forEach { it ->
                    val textSplited = it.split(":")
                    map[textSplited[0]] = textSplited[1]
                }
                return User(nickname=map["nickname"]!!,
                    email=map["email"]!!,
                    first_name=map["first_name"]!!,
                    last_name=map["last_name"]!!,
                    birthdate=map["birthdate"]!!
                )
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
