package com.infnet.battle_dual.model

import java.io.Serializable

class Room(val id : Int = 0,
           val name : String = "",
           val owner : Int = 0,
           val game_type : Int = 0,
           val game_id : Int = 0,
           val player_one : Int = 0,
           val player_two : Int = 0
           ) : Serializable

