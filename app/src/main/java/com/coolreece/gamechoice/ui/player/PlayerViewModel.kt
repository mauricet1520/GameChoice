package com.coolreece.gamechoice.ui.player

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.coolreece.gamechoice.data.Player
import com.coolreece.gamechoice.data.PlayerRepository

class PlayerViewModel(app: Application): AndroidViewModel(app)   {
    val playerRepository = PlayerRepository(app)

     val playerData: LiveData<List<Player>> = playerRepository.playerData
     val onePlayerData: LiveData<Player> = playerRepository.onePlayerData


    fun getPlayers() {
        Log.i("Service", "Calling getPlayers in ViewModel")
        playerRepository.getPlayers()
    }

    fun addPlayer(player: Player) {
        playerRepository.addPlayer(player)
    }

    fun getOnePlayer(name: String) {
        playerRepository.getPlayer(name)
    }

}