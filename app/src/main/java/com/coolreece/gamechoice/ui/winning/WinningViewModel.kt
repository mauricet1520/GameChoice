package com.coolreece.gamechoice.ui.winning

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.coolreece.gamechoice.data.win.WinningRepository
import com.coolreece.gamechoice.data.win.WinningTeam

class WinningViewModel(app: Application): AndroidViewModel(app) {
    private val winRepository = WinningRepository(app)
    fun addWins(winningTeam: WinningTeam) {
        winRepository.addWins(winningTeam)
    }
}