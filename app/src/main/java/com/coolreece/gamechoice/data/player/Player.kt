package com.coolreece.gamechoice.data.player

import com.google.gson.annotations.SerializedName

data class Player(
    var name: String? = null,
    var poolName: String? = null,
    var email: String? = null,
    var week: Int = 0,
    var teams: MutableList<String> = mutableListOf(),
    var points: Int = 0,
    var wins: Int = 0,
    @SerializedName("mondayNightTeam")
    var mondayNightTeam: String? = null,

    @SerializedName("winningTeams")
    var winningTeams: MutableList<String> = mutableListOf()
)