package com.infnet.battle_dual.model

import java.io.Serializable

class User (val nickname : String = "",
            val email : String = "",
            val active : Boolean = true,
            val first_name : String = "",
            val last_name : String = ""
            ) : Serializable {

}
