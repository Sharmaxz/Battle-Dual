package com.infnet.battle_dual.service

import com.infnet.battle_dual.model.User
import com.infnet.battle_dual.settings.AppPreferences
import com.infnet.battle_dual.shared.SessionManager
import khttp.*
import java.lang.Exception
import java.util.*


object SignUpService {

    private const val url = "https://battle-dual.herokuapp.com/api/signup/"

    fun post(nickname : String, email : String, password: String, firstName : String, LastName : String, birthdate: String) : Any {

        val map = mutableMapOf<String, String>()
        try {
            val response = get(url)
            if(response.statusCode == 201) {
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
                return mutableMapOf(
                    "response" to response.statusCode,
                    "result" to response.text
                )
            }
        }
        catch (e: Exception) {
            return mutableMapOf(
                "response" to 0
            )
        }
    }


}
