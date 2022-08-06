package com.coolreece.gamechoice.data.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    fun getAll(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Insert
    suspend fun insertGames(games: List<Game>)

    @Query("DELETE FROM games")
    suspend fun deleteAll()
}