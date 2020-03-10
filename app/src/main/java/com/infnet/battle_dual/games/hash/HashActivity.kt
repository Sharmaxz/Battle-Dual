package com.infnet.battle_dual.games.hash

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.infnet.battle_dual.R
import com.infnet.battle_dual.model.Hash
import com.infnet.battle_dual.model.Room
import com.infnet.battle_dual.shared.SessionManager
import kotlinx.android.synthetic.main.activity_hash.*

class HashActivity : AppCompatActivity() {

    val user = SessionManager.user
    lateinit var room : Room
    lateinit var hash : Hash
    lateinit var matrix : List<Int>
    lateinit var mark : Drawable
    val positions = listOf(btn1x1,btn1x2,btn1x3,btn2x1,btn2x2,btn2x3,btn3x1,btn3x2,btn3x3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hash)

        room = intent.extras!!["ROOM"] as Room
        hash = intent.extras!!["HASH"] as Hash

        if(user.nickname == room.player_one_label) {

        }
        else {

        }
    }

    override fun onResume() {
        super.onResume()
        render(convert())
    }

    fun convert() : List<Int> {
        matrix = hash.matrix.replace("([\\[\\]\"])".toRegex(), "").split(",").map { it.toInt() }
        return matrix
    }


    fun render(matrix : List<Int>) {
        var result : String = ""
        for (i in positions.indices){
            when (matrix[i]) {
                -1 -> result = ""
                0 -> result =  "X"
                1 -> result = "O"
            }
            positions[i].setText(result)
        }
    }

    fun mark(position : Int) {
        if(!hash.is_end && user.nickname == hash.turn_label) {
            positions[position].src = mark
        }
    }

}
