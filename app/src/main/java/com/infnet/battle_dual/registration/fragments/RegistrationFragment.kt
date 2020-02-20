package com.infnet.battle_dual.registration.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.infnet.battle_dual.R
import com.infnet.battle_dual.registration.RegistrationActivity
import kotlinx.android.synthetic.main.fragment_login_form.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class RegistrationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_form, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity!!.windowManager.defaultDisplay.getRealMetrics(resources.displayMetrics)
        btnRegistration.setOnClickListener { attemptRegistration() }
//        btnRegistration.setOnClickListener {
//            (activity as RegistrationActivity).registration()
//        }
    }

    private fun attemptRegistration() {
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
                    //registration(emailStr, passwordStr)
                }
            }
        } else {
            unlock()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun lock() {
        activity?.runOnUiThread(kotlinx.coroutines.Runnable {
//            email.isEnabled = false
//            password.isEnabled = false
//            btnLogin.isEnabled = false
//            btnRegistration.isEnabled = false
//            txtError.visibility = View.GONE
        })
    }

    private fun unlock() {
        activity?.runOnUiThread(kotlinx.coroutines.Runnable {
//            email.isEnabled = true
//            password.isEnabled = true
//            btnLogin.isEnabled = true
//            btnRegistration.isEnabled = true
        })
    }
}
