package com.coolreece.gamechoice.data.win

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import com.coolreece.gamechoice.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class WinningRepository(val app: Application) {

    fun addWins(wins: WinningTeam) {
        CoroutineScope(Dispatchers.IO).launch {
            val d = async { callWinningService(wins) }
            withTimeoutOrNull(200000L) {
                d.await()
            }
            Log.i("Service", "Calling getWins in Repository")
        }
    }

    @WorkerThread
    suspend fun callWinningService(wins: WinningTeam) {
        if (networkAvailable()) {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(WinService::class.java)

            val response = service.addWins(wins)

            Log.i("Service", "Calling Service ${response.isSuccessful} Body: ${response.body()}")
            Log.i("Service", "Code ${response.code()}")
            Log.i("Service", "Players: $response")
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