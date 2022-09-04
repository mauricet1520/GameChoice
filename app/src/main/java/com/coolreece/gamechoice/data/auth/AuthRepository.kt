package com.coolreece.gamechoice.data.auth

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreece.gamechoice.BASE_URL
import com.coolreece.gamechoice.data.game.Game
import com.coolreece.gamechoice.data.game.GameService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class AuthRepository(val app: Application) {

    val emailData = MutableLiveData<String>()

    fun signIn(authUser: AuthUser) {
        CoroutineScope(Dispatchers.IO).launch {
            withTimeout(130000L) {
                callService(authUser)
            }
            Log.i("Service", "Calling getGames in Repository")
        }
    }


    @WorkerThread
    suspend fun callService(authUser: AuthUser) {
        if (networkAvailable()) {

            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remote data", Toast.LENGTH_LONG).show()
            }
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = buildRetrofit(gson)

            val service = retrofit.create(AuthService::class.java)
            val response = service.authNewUser(authUser)
            Log.i("Service", "Calling Service ${response.isSuccessful} Body: ${response.body()}")


            val email = response.body()?.email ?: ""
            emailData.postValue(email)
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