package com.infnet.battle_dual.model

import java.io.Serializable

class User (val nickname : String = "",
            val email : String = "",
            val first_name : String = "",
            val last_name : String = "",
            val birthdate : String = ""
            ) : Serializable {

}
