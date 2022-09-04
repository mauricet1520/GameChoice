package com.coolreece.gamechoice.data.auth

data class AuthUserResponse (
        val idToken: String,
        val expiresIn: String,
        val email: String,
        val localId: String,
        val refreshToken: String)