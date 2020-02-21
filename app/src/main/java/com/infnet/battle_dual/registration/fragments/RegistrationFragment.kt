package com.infnet.battle_dual.registration.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.infnet.battle_dual.R
import com.infnet.battle_dual.registration.RegistrationActivity
import kotlinx.android.synthetic.main.fragment_registration_form.*
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
        val first_name = first_name.text.toString()
        val last_name = last_name.text.toString()
        val nickname = nickname.text.toString()
        val email = email.text.toString()

        var cancel = false
        var focusView : View? = null

        if (TextUtils.isEmpty(password.text.toString())) {
            password.error = getString(R.string.registration_error_password_empty)
            focusView = password
            cancel = true
        }

        if(TextUtils.isEmpty(confirm_password.text.toString())) {
            confirm_password.error = getString(R.string.registration_error_confirm_password_empty)
            focusView = confirm_password
            cancel = true
        }

        if(checkPassword(password.text.toString(), confirm_password.text.toString())) {
            confirm_password.error = getString(R.string.registration_error_password_diff)
            focusView = password
            cancel = false
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

    private fun checkPassword(password : String, confirm_password : String) : Boolean {
        if (password == confirm_password) {
            return true
        }
        return false
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
