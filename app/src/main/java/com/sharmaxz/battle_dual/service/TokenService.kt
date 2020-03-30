package com.sharmaxz.battle_dual.service

import com.sharmaxz.battle_dual.settings.AppPreferences
import kotlin.concurrent.thread

import khttp.DEFAULT_TIMEOUT
import khttp.*
import khttp.responses.Response
import khttp.structures.authorization.Authorization
import khttp.structures.files.FileLike
import kotlinx.coroutines.*
import com.google.gson.Gson
import java.lang.Exception


object TokenService {

    private val url = "https://battle-dual.herokuapp.com/o/token/"
    private val header = mapOf("Content-Type" to "application/x-www-form-urlencoded")

    fun post(nickname : String, password: String) : MutableMap<String, Any> {
        val data = mapOf(
            "client_secret" to AppPreferences.env["CLIENT_SECRET_KEY"],
            "client_id" to AppPreferences.env["CLIENT_PUBLIC_KEY"],
            "grant_type" to "password",
            "username" to nickname,
            "password" to password
        )

        val map = mutableMapOf<String, String>()
        var result = mutableMapOf<String, Any>()
        try {
            val response = post(url, headers=header, data=data)
            if(response.statusCode == 200) {
                response.text.replace("([{}\" ])".toRegex(), "").split(",").forEach { it ->
                    val textSplited = it.split(":")
                    map[textSplited[0]] = textSplited[1]
                }
                AppPreferences.bearer(map["access_token"] as String)

                result = mutableMapOf(
                    "response" to response.statusCode,
                    "result" to map
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

