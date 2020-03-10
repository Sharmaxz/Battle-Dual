package com.infnet.battle_dual.creation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.infnet.battle_dual.R
import com.infnet.battle_dual.creation.adapters.CreationAdapter
import com.infnet.battle_dual.creation.fragments.RankFragment
import com.infnet.battle_dual.creation.fragments.RoomFragment
import com.infnet.battle_dual.games.hash.HashActivity
import com.infnet.battle_dual.model.Hash
import com.infnet.battle_dual.model.Room
import com.infnet.battle_dual.registration.RegistrationActivity
import com.infnet.battle_dual.service.HashService
import com.infnet.battle_dual.service.UserService
import com.infnet.battle_dual.shared.DisplayMessage
import com.infnet.battle_dual.shared.SessionManager
import com.infnet.battle_dual.shared.Toolbar
import kotlinx.android.synthetic.main.activity_creation.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope


class CreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //metrics = resources.displayMetrics
        setContentView(R.layout.activity_creation)

        //Toolbar
        val toolbar = Toolbar(this, findViewById(R.id.toolbar), R.menu.menu)
        toolbar.titleEnabled(false)

        //TabLayout
        tablayout.addTab(tablayout.newTab().setText(R.string.creation_rank))
        tablayout.tabGravity = TabLayout.GRAVITY_CENTER

        val adapter = CreationAdapter(supportFragmentManager)
        adapter.addFragment(RoomFragment(), getString(R.string.creation_room))
        adapter.addFragment(RankFragment(), getString(R.string.creation_rank))
        pager.adapter = adapter
        tablayout.setupWithViewPager(pager)
    }

    fun selectedRoom(room : Room) {
        GlobalScope.launch {
            supervisorScope {
                when(room.game_type) {
                    14 -> hash(room)
                }
            }
        }
    }

    private fun hash(room : Room) {
        val hash = HashService.get(room.game_id)

        runOnUiThread(kotlinx.coroutines.Runnable {
            if(hash::class.java.simpleName == "Hash") {
                    openHashActivity(room, hash as Hash)
            }
            else {
                DisplayMessage.show("It wasn't possible to start the game.","long")
            }
        })
    }

    private fun openHashActivity(room : Room, hash : Hash)
    {
        intent = Intent(this, HashActivity::class.java)
        intent.putExtra("ROOM", room)
        intent.putExtra("HASH", hash)
        startActivity(intent)
    }
}
