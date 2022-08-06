package com.coolreece.gamechoice.data.game

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.coolreece.gamechoice.DATA_KEY
import com.coolreece.gamechoice.FILE_URL
import com.coolreece.gamechoice.MESSAGE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset

class GameWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val data = withContext(Dispatchers.IO) {
            setProgress(workDataOf(MESSAGE_KEY to "Doing work!"))
            delay(1000)
            setProgress(workDataOf(MESSAGE_KEY to "Doing more work!"))
            delay(1000)
            setProgress(workDataOf(MESSAGE_KEY to "Almost!"))
            delay(1000)
            Log.i("Worker", "Worker working")
            val url = URL(FILE_URL)
            return@withContext url.readText(Charset.defaultCharset())
        }
        val result = workDataOf(DATA_KEY to data)
        return Result.success(result)
    }
}