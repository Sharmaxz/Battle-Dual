package com.infnet.battle_dual.registration.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.infnet.battle_dual.R
import com.infnet.battle_dual.model.User
import com.infnet.battle_dual.registration.RegistrationActivity
import com.infnet.battle_dual.service.SignUpService
import com.infnet.battle_dual.service.TokenService
import com.infnet.battle_dual.service.UserService
import com.infnet.battle_dual.shared.SessionManager
import kotlinx.android.synthetic.main.fragment_login_form.*
import kotlinx.android.synthetic.main.fragment_registration_form.*
import kotlinx.android.synthetic.main.fragment_registration_form.btnRegistration
import kotlinx.android.synthetic.main.fragment_registration_form.email
import kotlinx.android.synthetic.main.fragment_registration_form.password
import kotlinx.android.synthetic.main.fragment_registration_form.txtError
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
        resetError()

        var cancel = false
        var focusView : View? = null

        if (isEmpty(first_name.text!!)) {
            first_name.error = getString(R.string.registration_error_empty)
            focusView = first_name
            cancel = true
        }

        if (isEmpty(last_name.text!!)) {
            last_name.error = getString(R.string.registration_error_empty)
            focusView = last_name
            cancel = true
        }

        if (isEmpty(nickname.text!!)) {
            nickname.error = getString(R.string.registration_error_empty)
            focusView = nickname
            cancel = true
        }

        // Check for a valid email address.
        if (isEmpty(email.text!!)) {
            email.error = getString(R.string.registration_error_empty)
            focusView = email
            cancel = true
        } else if (!isEmailValid(email.text!!)) {
            email.error = getString(R.string.registration_error_empty)
            focusView = email
            cancel = true
        }

        if (isEmpty(password.text!!)) {
            password.error = getString(R.string.registration_error_empty)
            focusView = password
            cancel = true
        }

        if(isEmpty(confirm_password.text!!)) {
            confirm_password.error = getString(R.string.registration_error_empty)
            focusView = confirm_password
            cancel = true
        } else if(!checkPassword(password.text.toString(), confirm_password.text.toString())) {
            confirm_password.error = getString(R.string.registration_error_password_diff)
            focusView = confirm_password
            cancel = true
        }

        if (!cancel) {
            focusView = null
            GlobalScope.launch {
                supervisorScope {
                    registration(first_name.text.toString(),
                                 last_name.text.toString(),
                                 nickname.text.toString(),
                                 email.text.toString(),
                                 password.text.toString()
                    )
                }
            }
        } else {
            unlock()
        }
    }

    //region Validators

    private fun isEmpty(value : Editable) : Boolean {
        if (TextUtils.isEmpty(value)) {
            return true
        }
        return false
    }

    private fun isEmailValid(email: Editable) : Boolean {
        return email.contains("@")
    }

    private fun checkPassword(password : String, confirm_password : String) : Boolean {
        if (password == confirm_password) {
            return true
        }
        return false
    }

    //endregion

    private fun lock() {
        activity?.runOnUiThread(kotlinx.coroutines.Runnable {
            first_name.isEnabled = false
            last_name.isEnabled = false
            nickname.isEnabled = false
            email.isEnabled = false
            password.isEnabled = false
            confirm_password.isEnabled = false
            btnRegistration.isEnabled = false
            txtError.visibility = View.GONE
        })
    }

    private fun unlock() {
        activity?.runOnUiThread(kotlinx.coroutines.Runnable {
            first_name.isEnabled = true
            last_name.isEnabled = true
            nickname.isEnabled = true
            email.isEnabled = true
            password.isEnabled = true
            confirm_password.isEnabled = true
            btnRegistration.isEnabled = true
        })
    }

    private fun timeout() {
        activity?.runOnUiThread(kotlinx.coroutines.Runnable {
            txtError.visibility = View.VISIBLE
            txtError.text = getString(R.string.login_error_timeout)
        })
    }

    private fun resetError() {
        activity?.runOnUiThread(kotlinx.coroutines.Runnable {
            first_name.error = null
            last_name.error = null
            nickname.error = null
            email.error = null
            password.error = null
            confirm_password.error = null
            btnRegistration.error = null
        })
    }

    private fun registration(first_name : String, last_name : String, nickname : String, email : String, password: String) {
        val user =  SignUpService.post(first_name, last_name,nickname, email, password)

        if (user::class.java.simpleName != "User") {
            timeout()
            unlock()
        } else {
            SessionManager.create(user as User)
            (activity as RegistrationActivity).openCreation()
        }
    }
}
