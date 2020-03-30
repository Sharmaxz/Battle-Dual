package com.sharmaxz.battle_dual.creation.adapters

import com.sharmaxz.battle_dual.*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sharmaxz.battle_dual.model.Room
import com.sharmaxz.battle_dual.shared.SessionManager
import kotlinx.android.synthetic.main.creation_list.view.*

open class RoomAdapter(private val context : Context,
                       private val rooms : List<Room>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.creation_list, parent, false)

        val room = getItem(position) as Room
        rowView.title.text = room.name
        rowView.owner.text = context.getString(R.string.creation_for) + " " + room.owner_label

        when {
            room.turn_label == SessionManager.user.nickname -> {
                rowView.turn.visibility = View.VISIBLE
                rowView.turn.text = context.getString(R.string.creation_turn)
            }
            room.is_end -> {
                rowView.turn.visibility = View.VISIBLE
                rowView.turn.text = context.getString(R.string.creation_is_over)
            }
            else -> {
                rowView.turn.visibility = View.GONE
            }
        }

        return rowView
    }

    override fun getCount(): Int {
        return rooms.size
    }

    override fun getItem(position: Int): Any {
        return rooms[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }
}
