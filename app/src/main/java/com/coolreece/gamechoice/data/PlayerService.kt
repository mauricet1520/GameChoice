package com.coolreece.gamechoice.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlayerService {
    @GET("/players/{week}")
    suspend fun getPlayers(@Path("week") week: String): Response<List<Player>>

    @POST("/players")
    suspend fun addPlayer(@Body player: Player): Response<String>
}