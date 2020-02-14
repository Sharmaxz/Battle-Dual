package com.infnet.battle_dual.service

import com.infnet.battle_dual.settings.AppPreferences
import kotlin.concurrent.thread

import khttp.DEFAULT_TIMEOUT
import khttp.responses.Response
import khttp.structures.authorization.Authorization
import khttp.structures.files.FileLike
import com.google.gson.Gson


class TokenService {

    val url = "https://battle-dual.herokuapp.com/o/token/"
    val header = mapOf("Content-Type" to "application/x-www-form-urlencoded")

    fun asyncRequest(method: String, url: String, headers: Map<String, String> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf(), callback: Response.() -> Unit): Unit {
        thread {
            callback(khttp.request(method, url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files))
        }
    }


    fun post(nickname : String, password: String) {
        val data = mapOf(
            "client_secret" to AppPreferences.env["CLIENT_SECRET_KEY"],
            "client_id" to AppPreferences.env["CLIENT_PUBLIC_KEY"],
            "grant_type" to "password",
            "username" to nickname,
            "password" to password
        )
        asyncRequest("POST", url, headers=header, data=data) {
            println("Status Code: $statusCode")
            println("Response Text: $text")
            //AppPreferences.Bearer()
        }
    }

}

