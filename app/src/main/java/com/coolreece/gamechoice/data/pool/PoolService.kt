package com.coolreece.gamechoice.data.pool

import com.coolreece.gamechoice.data.game.Game
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PoolService {
    @GET("/leagues/getLeague/{name}")
    suspend fun getLeague(@Path("name") name: String): Response<Pool>

    @GET("/leagues/allLeagues")
    suspend fun allLeagues(): Response<List<Pool>>

    @POST("/leagues/addNewLeague")
    suspend fun addNewLeague(@Body pool: Pool): Response<Any>
}