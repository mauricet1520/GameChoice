package com.coolreece.gamechoice.data.pool

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

class PoolRepository(val app: Application) {

     val poolData = MutableLiveData(listOf<Pool>())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeout(130000L) {
                callServiceAllLeagues()
            }
        }
    }

    fun getPoolData() {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeout(130000L) {
                callServiceAllLeagues()
            }
            Log.i("PoolRepo", "Calling getPoolData in Repository")
        }
    }

    @WorkerThread
    suspend fun callServiceAllLeagues() {
        if (networkAvailable()) {

            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remote data", Toast.LENGTH_LONG).show()
            }
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(PoolService::class.java)
            val leagueList = service.allLeagues()
            Log.i("GameRepo", "Calling Service ${leagueList.isSuccessful} Body: ${leagueList.body()}")
            Log.i("GameRepo", "Code ${leagueList.code()}")
            Log.i("GameRepo", "Error $leagueList")
            val games = leagueList.body() ?: emptyList()
            poolData.postValue(games)
        }else {
            Log.i("GameRepo", "Error No Network")
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