package com.coolreece.gamechoice.data

data class Game(
    var week: String? = null,
    var home: String,
    var away: String,
    var mondayNight: Boolean? = null
)