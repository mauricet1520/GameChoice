package com.coolreece.gamechoice.data.player

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreece.gamechoice.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class PlayerRepository(val app: Application) {

    val playerData = MutableLiveData(listOf<Player>())
    val onePlayerData = MutableLiveData<Player>()

    init {
        getPlayers()
    }

    fun getPlayers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeoutOrNull(200000L) {
                    callService()
                    Log.i("Service", "Calling getPlayers in Repository")
                }
            }catch (e: Throwable) {
                Log.e("Service", "Error", e)
            }

        }
    }

    fun getPlayer(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeoutOrNull(200000L) {
                    callGetPlayerService(name)
                }
            }catch (e: Throwable) {

            }

        }
    }

    fun addPlayer(player: Player) {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeoutOrNull(200000L) {
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
            playerData.postValue(players.body() ?: emptyList())
        }else {
            Log.i("Service", "Error No Network")
        }

    }

    @WorkerThread
    suspend fun callGetPlayerService(name: String) {
        if (networkAvailable()) {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(PlayerService::class.java)
            val playerServiceResponse = service.getPlayer("week 1", name)
            onePlayerData.postValue(playerServiceResponse.body())
            Log.i("Service", "Calling Service ${playerServiceResponse.isSuccessful} " +
                    "Body: ${playerServiceResponse.body()}")
            Log.i("Service", "Code ${playerServiceResponse.code()}")
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

            Log.i("Service", "Calling Service ${players.isSuccessful} Body: ${players.body()}")
            Log.i("Service", "Code ${players.code()}")
            Log.i("Service", "Players: $players")
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

        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }
}