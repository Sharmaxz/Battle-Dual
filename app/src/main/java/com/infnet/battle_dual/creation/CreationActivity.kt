package com.infnet.battle_dual.creation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.infnet.battle_dual.R
import com.infnet.battle_dual.creation.fragments.CreationPageAdapter
import com.infnet.battle_dual.creation.fragments.RankFragment
import com.infnet.battle_dual.creation.fragments.RoomFragment
import com.infnet.battle_dual.shared.Toolbar
import kotlinx.android.synthetic.main.activity_creation.*


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

        val adapter = CreationPageAdapter(supportFragmentManager)
        adapter.addFragment(RoomFragment(), getString(R.string.creation_room))
        adapter.addFragment(RankFragment(), getString(R.string.creation_rank))
        pager.adapter = adapter
        tablayout.setupWithViewPager(pager)

    }

}
