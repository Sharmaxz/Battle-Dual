package com.sharmaxz.battle_dual.model

import java.io.Serializable

class Hash(val id : Int = 0,
           var turn : String = "",
           var turn_label : String = "",
           val turn_count : Int = 0,
           val matrix : String = "[[-1, -1, -1],[-1, -1, -1],[-1, -1, -1]]",
           val is_end : Boolean = false,
           val is_draw : Boolean = false) : Serializable
