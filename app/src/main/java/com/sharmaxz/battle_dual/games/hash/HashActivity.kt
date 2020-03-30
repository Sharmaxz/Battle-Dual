package com.sharmaxz.battle_dual.games.hash

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sharmaxz.battle_dual.R
import com.sharmaxz.battle_dual.model.Hash
import com.sharmaxz.battle_dual.model.Room
import com.sharmaxz.battle_dual.service.HashService
import com.sharmaxz.battle_dual.service.RoomService
import com.sharmaxz.battle_dual.shared.DisplayMessage
import com.sharmaxz.battle_dual.shared.SessionManager
import kotlinx.android.synthetic.main.activity_hash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class HashActivity : AppCompatActivity() {

    //Players
    val user = SessionManager.user
    var player2 : MutableMap<String, Any> = mutableMapOf()

    //Settings
    lateinit var room : Room
    lateinit var hash : Hash


    lateinit var mark : String
    lateinit var positions : MutableList<Button> //It are the buttons position.
    var matrix : MutableList<MutableList<Int>>  = mutableListOf() //It's a matrix, list of list.
    var board : MutableList<Int>  = mutableListOf() //It's the single list of the matrix.
    var draw : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hash)

        positions = mutableListOf(btn1x1,btn1x2,btn1x3,btn2x1,btn2x2,btn2x3,btn3x1,btn3x2,btn3x3)

        room = intent.extras!!["ROOM"] as Room
        hash = intent.extras!!["HASH"] as Hash
        draw = hash.is_draw
    }

    override fun onResume() {
        super.onResume()

        name.text = room.name
        if(!hash.is_end) {

            if (user.nickname == hash.turn_label) {
                turn.text = getString(R.string.hash_your_turn)
            } else {
                turn.text = getString(R.string.hash_wait_turn)
                waitOtherPlayer()
            }

            if (user.nickname == room.player_one_label) {
                player2["player_label"] = room.player_two_label
                player2["id"] = room.player_two
                mark = "X"
            } else {
                player2["player_label"] = room.player_one_label
                player2["id"] = room.player_one
                mark = "O"
            }

        }
        else {
            if(!draw) {
                if(user.nickname != hash.turn_label) {
                    turn.text = getString(R.string.hash_won)
                } else {
                    turn.text = getString(R.string.hash_lose)
                }
            } else {
                turn.text = getString(R.string.hash_draw)
            }
        }

        render(convert())
    }

    fun convert() : List<Any> {
        board = hash.matrix.replace("([\\[\\]\\s\"])".toRegex(), "").split(",").map { it.toInt() } as MutableList<Int>
        matrix = mutableListOf(
            mutableListOf(board[0], board[1], board[2]),
            mutableListOf(board[3], board[4], board[5]),
            mutableListOf(board[6], board[7], board[8]))

        return board
    }

    fun render(board : List<Any>) {
        var result = ""
        for (i in positions.indices){
            when (board[i]) {
                -1 -> result = ""
                0 -> result =  "X"
                1 -> result = "O"
            }
            positions[i].text = result
        }
    }

    fun unrender() : String {
        val positionsString = mutableListOf<String>()
        board = mutableListOf()
        for (i in positions) {
                when (i.text) {
                "" ->  {
                    positionsString.add("-1")
                    board.add(-1)
                }
                "X" -> {
                    positionsString.add("0")
                    board.add(0)
                }
                "O" -> {
                    positionsString.add("1")
                    board.add(1)
                }
            }
        }

        matrix = mutableListOf(
            mutableListOf(board[0], board[1], board[2]),
            mutableListOf(board[3], board[4], board[5]),
            mutableListOf(board[6], board[7], board[8]))

        return "[[" +
                positionsString[0] + ", " + positionsString[1] + ", " + positionsString[2] + "], [" +
                positionsString[3] + ", " + positionsString[4] + ", " + positionsString[5] + "], [" +
                positionsString[6] + ", " + positionsString[7] + ", " + positionsString[8] +
                "]]"
    }

    fun mark(v : View) {
        val position = v as Button
        if(!hash.is_end && position.text == "" && user.nickname == hash.turn_label) {
            position.text = mark
            turn.text =  getString(R.string.hash_wait_turn)
            hash.turn_label = player2["player_label"].toString()

            val body = mutableMapOf(
                "turn" to player2["id"],
                "turn_count" to hash.turn_count + 1,
                "turn_label" to player2["player_label"],
                "matrix" to unrender(),
                "is_end" to isEnd(positions.indexOf(position)),
                "is_draw" to draw
            )

            if (body["is_end"] == true)  {
                turn.text = getString(R.string.hash_won)
            }

            GlobalScope.launch {
                supervisorScope {
                    var response = HashService.patch(hash.id, body)

                    if (response::class.java.simpleName != "Hash") {
                        DisplayMessage.show("Retrying to connect with the server again!")
                        response = HashService.patch(hash.id, body)

                        if (response::class.java.simpleName != "Hash") {
                            DisplayMessage.show("Try it later!")
                        }
                    }
                }
            }
            waitOtherPlayer()
        }
    }

    fun isEnd(position : Int) : Boolean {

        //region FUCK THE BULLSHIT
        for(i in 0..2) {
            if (matrix[i][0] != -1 && matrix[i][1] != -1 && matrix[i][2] != -1 &&
                matrix[i][0] == matrix[i][1] &&
                matrix[i][1] == matrix[i][2] &&
                matrix[i][0] == matrix[i][2]
            ) {
                return true
            }

            if (matrix[0][i] != -1 && matrix[1][i] != -1 && matrix[2][i] != -1 &&
                matrix[0][i] == matrix[1][i] &&
                matrix[1][i] == matrix[2][i] &&
                matrix[0][i] == matrix[2][i]
            ) {
                return true
            }
        }

        if (matrix[0][0] != -1 && matrix[1][1] != -1 && matrix[2][2] != -1 &&
            matrix[0][0] == matrix[1][1] &&
            matrix[1][1] == matrix[2][2] &&
            matrix[0][0] == matrix[2][2]
        ) {
            return true
        }

        if (matrix[0][2] != -1 && matrix[1][1] != -1 && matrix[2][0] != -1 &&
            matrix[0][2] == matrix[1][1] &&
            matrix[1][1] == matrix[2][0] &&
            matrix[0][2] == matrix[2][0]
        ) {
            return true
        }
        //endregion FUCK THE BULLSHIT

        if(hash.turn_count + 1 >= 9) {
            draw = true
            return true
        }

        return false
    }

    fun waitOtherPlayer() {
        Handler().postDelayed({
            GlobalScope.launch {
                supervisorScope {
                    val hash = HashService.get(hash.id)
                    if (hash::class.java.simpleName == "Hash") {
                        this@HashActivity.hash  = hash as Hash
                        this@HashActivity.draw = hash.is_draw
                    }
                }
            }
        }, 500)

        Handler().postDelayed({
            if (user.nickname == hash.turn_label) {
                onResume()
            }
            else {
                waitOtherPlayer()
            }
        }, 500)

    }

}
