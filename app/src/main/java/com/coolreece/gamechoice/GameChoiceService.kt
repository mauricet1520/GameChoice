package com.coolreece.gamechoice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class GameChoiceService : Service() {
    private val binder = MyServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun doSomething() {
        Log.i("GameChoiceService", "This service is running")
    }

    inner class MyServiceBinder : Binder() {
        fun getService() = this@GameChoiceService
    }
}