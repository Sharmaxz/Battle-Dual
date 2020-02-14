package com.infnet.battle_dual.creation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.infnet.battle_dual.R
import com.infnet.battle_dual.shared.Toolbar


class CreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //metrics = resources.displayMetrics
        setContentView(R.layout.activity_registration)

        //Toolbar
        val toolbar = Toolbar(this, findViewById(R.id.toolbar), R.menu.menu)
        toolbar.titleEnabled(false)


        //Room List
        room()
    }

    private fun room() {
//        val adapter = RoomAdapter(this, rooms)
//
//        val listView = findViewById<ListView>(R.id.room_listview)
//        listView.setAdapter(adapter)
    }
}
