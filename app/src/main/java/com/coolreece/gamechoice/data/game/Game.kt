package com.coolreece.gamechoice.data.game

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    var gameId: Int,
    var week: String? = null,
    var home: String,
    var away: String,
    var mondayNight: Boolean? = null
)