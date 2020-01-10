package com.infnet.battle_dual.registration.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.infnet.battle_dual.R
import kotlinx.android.synthetic.main.fragment_login_form.*

import khttp.*
import kotlinx.coroutines.*

class LoginFragment : Fragment() {

    var timeout = false
    var error = false

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.fragment_login_form, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnLogin.setOnClickListener { attemptLogin() }
        btnRegistration.setOnClickListener {}
    }

    private fun attemptLogin() {
        //Avoid other login attempt
        lock()

        // Reset errors.
        txtError.visibility = View.GONE
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView : View? = null

        if (TextUtils.isEmpty(passwordStr)) {
            password.error = getString(R.string.login_error_password_empty)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.login_error_email_empty)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.login_error_email)
            focusView = email
            cancel = true
        }

        if (!cancel) {
            focusView = null
            GlobalScope.launch {
                supervisorScope {
                    login()
                }
            }
        } else {
            unlock()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun login() {
        val r = get("https://httpstat.us/522")
        when (r.statusCode) {
            200 -> {

            }
            522 -> {
                activity?.runOnUiThread {
                    timeout()
                    unlock()
                }
            }
            else -> {
                activity?.runOnUiThread {
                    error()
                    unlock()
                }
            }
        }
    }

    private fun lock() {
        email.isEnabled = false
        password.isEnabled = false
        btnLogin.isEnabled = false
        btnRegistration.isEnabled = false
        txtError.visibility = View.GONE
    }

    private fun unlock() {
        email.isEnabled = true
        password.isEnabled = true
        btnLogin.isEnabled = true
        btnRegistration.isEnabled = true
    }

    private fun timeout() {
        txtError.visibility = View.VISIBLE
        txtError.text = getString(R.string.login_error_timeout)
    }

    private fun error() {
        txtError.visibility = View.VISIBLE
        txtError.text = getString(R.string.login_error)
    }

}
