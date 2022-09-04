package com.coolreece.gamechoice.data.pool

import com.coolreece.gamechoice.data.player.Player

data class WeeklyWins (
    var week: Int = 0,
    var teams: List<String>? = null,
    var totalPoints: Int? = null,
    var winner: String? = null,
    var topPlayers: MutableList<Player> = mutableListOf()
)
