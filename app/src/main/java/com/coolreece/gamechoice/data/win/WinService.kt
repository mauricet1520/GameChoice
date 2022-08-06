package com.coolreece.gamechoice.data.win

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WinService {

    @POST("/wins")
    suspend fun addWins(@Body wins: WinningTeam): Response<String>
}