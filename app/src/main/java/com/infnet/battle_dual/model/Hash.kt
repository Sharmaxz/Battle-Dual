package com.infnet.battle_dual.model

import java.io.Serializable

class Hash(val id : Int = 0,
           val turn : String = "",
           val turn_label : String = "",
           val turn_count : Int = 0,
           val matrix : String = "[[-1, -1, -1],[-1, -1, -1],[-1, -1, -1]]",
           val is_end : Boolean = false) : Serializable
