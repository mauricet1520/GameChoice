package com.coolreece.gamechoice.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.coolreece.gamechoice.data.auth.AuthRepository
import com.coolreece.gamechoice.data.auth.AuthUser
import com.coolreece.gamechoice.data.game.Game
import com.coolreece.gamechoice.data.game.GameRepository


class AuthViewModel(app: Application): AndroidViewModel(app)  {

    val authRepository = AuthRepository(app)

    val emailData: LiveData<String> = authRepository.emailData

    fun signIn(authUser: AuthUser) {
        Log.i("Service", "Calling getGames in ViewModel")
        authRepository.signIn(authUser)
    }

}