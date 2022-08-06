package com.coolreece.gamechoice.data.game

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreece.gamechoice.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class GameRepository(val app: Application) {

     val gameData = MutableLiveData(listOf<Game>())
    private val gameDao = GameDatabase.getDatabase(app).gameDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeout(130000L) {
                val data = gameDao.getAll()
                if (data.isEmpty()) {
                    callService()
                } else {
                    gameData.postValue(data)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(app, "Using local data", Toast.LENGTH_LONG).show()
                    }
                }
            }
            Log.i("Service", "Calling getGames in Repository")
        }
    }

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

            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remote data", Toast.LENGTH_LONG).show()
            }
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(GameService::class.java)
            val gameList = service.getGames("week 1")
            Log.i("Service", "Calling Service ${gameList.isSuccessful} Body: ${gameList.body()}")
            Log.i("Service", "Code ${gameList.code()}")
            Log.i("Service", "Error $gameList")
            val games = gameList.body() ?: emptyList()
            gameData.postValue(games)
            gameDao.deleteAll()
            gameDao.insertGames(games)
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