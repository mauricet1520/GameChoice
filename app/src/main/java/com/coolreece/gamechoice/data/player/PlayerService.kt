package com.coolreece.gamechoice.data.player

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlayerService {
    @GET("/players/{poolName}")
    suspend fun getPlayers(@Path("poolName") poolName: String): Response<List<Player>>

    @GET("/players/{poolName}/{email}")
    suspend fun getPlayer(@Path("poolName") poolName: String,
    @Path("email") email: String): Response<Player>

    @POST("/players")
    suspend fun addPlayer(@Body player: Player): Response<String>
}