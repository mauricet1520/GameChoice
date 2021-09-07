package com.coolreece.gamechoice.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolreece.gamechoice.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class PlayerRepository(val app: Application) {

    val playerData = MutableLiveData(listOf<Player>())

    fun getPlayers() {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeout(130000L) {
                callService()
            }
            Log.i("Service", "Calling getPlayers in Repository")
        }
    }

    fun addPlayer(player: Player) {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeout(130000L) {
                callPlayerService(player)
            }
            Log.i("Service", "Calling addPlayer in Repository")
        }
    }

    @WorkerThread
    suspend fun callService() {
        if (networkAvailable()) {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(PlayerService::class.java)
            val players = service.getPlayers("week 1")


            Log.i("Service", "Calling Service ${players.isSuccessful} Body: ${players.body()}")
            Log.i("Service", "Code ${players.code()}")
            Log.i("Service", "Error $players")
            playerData.postValue(players.body() ?: emptyList())
        }else {
            Log.i("Service", "Error No Network")
        }

    }

    @WorkerThread
    suspend fun callPlayerService(player: Player) {
        if (networkAvailable()) {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(PlayerService::class.java)
            val players = service.addPlayer(player)

//            val playerList: MutableList<Player> = playerData.value as MutableList<Player>
//            playerList.add(player)

//            playerData.value = playerList

            Log.i("Service", "Calling Service ${players.isSuccessful} Body: ${players.body()}")
            Log.i("Service", "Code ${players.code()}")
            Log.i("Service", "Error $players")
        }else {
            Log.i("Service", "Error No Network")
        }

    }

    @Suppress("DEPRECATION")
    fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    private fun buildRetrofit(gson: Gson?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }
}