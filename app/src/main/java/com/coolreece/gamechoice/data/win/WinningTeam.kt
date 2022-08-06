package com.coolreece.gamechoice.data.win

import com.coolreece.gamechoice.data.player.Player
import com.google.gson.annotations.SerializedName

data class WinningTeam (
        var week: String? = null,
        @SerializedName("mondayNightTeam")
        var teams: MutableList<String> = mutableListOf(),
        var totalPoints: Int? = null,
        var winner: String? = null,
        var topPlayers: MutableList<Player> = mutableListOf()
)