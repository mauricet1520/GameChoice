package com.coolreece.gamechoice.data.game

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.coolreece.gamechoice.MESSAGE_KEY
import com.coolreece.gamechoice.data.player.PlayerRepository
import com.coolreece.gamechoice.ui.player.PlayerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

//class GameWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
//    val application: Application = context.applicationContext as Application
//    val playerViewModel: PlayerViewModel = PlayerViewModel(application)
//
//    val playerRepository = PlayerRepository(application)
//    override suspend fun doWork(): Result {
//
//        withContext(Dispatchers.IO) {
//            try {
//                playerViewModel.getPlayers()
//                setProgress(workDataOf(MESSAGE_KEY to "Doing work!"))
//                delay(1000)
//                setProgress(workDataOf(MESSAGE_KEY to "Doing more work!"))
//                delay(1000)
//                setProgress(workDataOf(MESSAGE_KEY to "Almost!"))
//                delay(1000)
//                Log.i("Worker", "Worker working")
//                return@withContext Result.success()
//            } catch (error: Throwable) {
//                Log.i("Worker", "Worker failed", error)
//
//                Result.failure()
//            }
//        }
//        return Result.success()
//    }


//}