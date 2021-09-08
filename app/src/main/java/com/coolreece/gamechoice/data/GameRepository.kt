package com.coolreece.gamechoice.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.coolreece.gamechoice.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.math.log

class GameRepository(val app: Application) {

     val gameData = MutableLiveData(listOf<Game>())

    fun getGames() {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeout(130000L) {
                callService()
            }
            Log.i("Service", "Calling getGames in Repository")
        }
    }

    @WorkerThread
    suspend fun callService() {
        if (networkAvailable()) {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(GameService::class.java)
            val gameList = service.getGames("week 1")
            Log.i("Service", "Calling Service ${gameList.isSuccessful} Body: ${gameList.body()}")
            Log.i("Service", "Code ${gameList.code()}")
            Log.i("Service", "Error $gameList")
            gameData.postValue(gameList.body() ?: emptyList())
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
            .addConverterFactory(GsonConverterFactory.create(gson!!))
            .build()

    }
}