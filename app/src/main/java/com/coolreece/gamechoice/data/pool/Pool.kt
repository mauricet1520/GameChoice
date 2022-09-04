package com.coolreece.gamechoice.data.pool

import com.coolreece.gamechoice.data.player.Player

data class Pool (
    val private: Boolean = false,
    val name: String? = null,
    val weeklyWins: WeeklyWins? = null,
    val players: List<Player> = ArrayList())
