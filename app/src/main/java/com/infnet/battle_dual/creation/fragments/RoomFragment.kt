package com.infnet.battle_dual.creation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.infnet.battle_dual.R
import com.infnet.battle_dual.creation.adapters.RoomAdapter
import com.infnet.battle_dual.model.Room
import com.infnet.battle_dual.model.User
import com.infnet.battle_dual.registration.RegistrationActivity
import com.infnet.battle_dual.service.RoomService
import com.infnet.battle_dual.service.UserService
import com.infnet.battle_dual.shared.SessionManager
import kotlinx.android.synthetic.main.fragment_creation_room.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class RoomFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creation_room, container, false)

    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            supervisorScope {
                list()
            }
        }
    }

    fun list() {
        val rooms = RoomService.get()
//        if (rooms::class.java.simpleName != "User") {
//
//        } else {
//            SessionManager.create(user as User)
//            (activity as RegistrationActivity).openCreation()
//        }
        val adapter = RoomAdapter(context!!, rooms as MutableList<Room>)
        listview.adapter = adapter
        listview.setOnItemClickListener { parent, view, position, id ->

        }
    }

}
