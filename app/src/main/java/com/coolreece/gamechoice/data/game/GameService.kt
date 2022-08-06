package com.coolreece.gamechoice.data.game

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GameService {
    @GET("/games/{week}")
    suspend fun getGames(@Path("week") week: String): Response<List<Game>>
}