package com.coolreece.gamechoice.ui.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.coolreece.gamechoice.data.GameRepository


class GameViewModel(app: Application): AndroidViewModel(app)  {

    val gameRepository = GameRepository(app)

    val gameData = gameRepository.gameData

    fun getGames() {
        Log.i("Service", "Calling getGames in ViewModel")
        gameRepository.getGames()
    }

}