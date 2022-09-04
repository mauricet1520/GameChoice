package com.coolreece.gamechoice.ui.pool

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.coolreece.gamechoice.data.game.Game
import com.coolreece.gamechoice.data.game.GameRepository
import com.coolreece.gamechoice.data.pool.Pool
import com.coolreece.gamechoice.data.pool.PoolRepository


class PoolViewModel(app: Application): AndroidViewModel(app)  {

    val poolRepository = PoolRepository(app)

    val poolData: LiveData<List<Pool>> = poolRepository.poolData

    fun allLeague() {
        Log.i("Service", "Calling getGames in ViewModel")
        poolRepository.getPoolData()
    }
}