package com.infnet.battle_dual.service

import com.infnet.battle_dual.settings.AppPreferences
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

    private fun asyncRequest(method: String, url: String, headers: Map<String, String> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf(), callback: Response.() -> Unit): Unit {
        thread {
            callback(khttp.request(method, url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files))
        }
    }

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


//        asyncRequest("POST", url, headers=header, data=data)  {
//            if(statusCode == 200) {
//                val map = mutableMapOf<String, String>()
//                text.replace("{", "").replace("}", "").replace("\"", "").split(",").forEach { it ->
//                    val textSplited = it.split(":")
//                    map[textSplited[0]] = textSplited[1]
//                }
//                AppPreferences.bearer(map["access_token"] as String)
//            }
//            else if(statusCode == 502) {
//            }
//        }


    }

}

