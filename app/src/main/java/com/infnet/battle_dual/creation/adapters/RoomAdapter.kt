package com.infnet.battle_dual.creation.adapters

import com.infnet.battle_dual.*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.infnet.battle_dual.model.Room

open class RoomAdapter(context : Context,
                       rooms : MutableList<Room>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.listview_item, parent, false)

        return rowView
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return 'a'
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }
}