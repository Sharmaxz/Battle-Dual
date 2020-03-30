package com.sharmaxz.battle_dual.model

import java.io.Serializable

class Room(val id : Int = 0,
           val name : String = "",
           val game_id : Int = 0,
           val game_type : Int = 0,
           val turn : String = "",
           val turn_label : String = "",
           val is_end : Boolean = false,
           val owner : Int = 0,
           val owner_label : String = "",
           val player_one : Int = 0,
           val player_one_label : String = "",
           val player_two : Int = 0,
           val player_two_label : String = ""
           ) : Serializable

