package com.coolreece.gamechoice.data.auth

import com.coolreece.gamechoice.data.player.Player
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/signIn")
    suspend fun signIn(@Body authUser: AuthUser): Response<AuthUserResponse>

    @POST("/authNewUser")
    suspend fun authNewUser(@Body authUser: AuthUser): Response<AuthUserResponse>
}