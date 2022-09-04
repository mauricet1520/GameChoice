package com.coolreece.gamechoice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.*
//import com.coolreece.gamechoice.data.game.GameWorker
import com.coolreece.gamechoice.data.player.Player
import com.coolreece.gamechoice.ui.auth.AuthViewModel
import com.coolreece.gamechoice.ui.composable.*
import com.coolreece.gamechoice.ui.game.GameViewModel
import com.coolreece.gamechoice.ui.player.PlayerViewModel
import com.coolreece.gamechoice.ui.pool.PoolViewModel
import com.coolreece.gamechoice.ui.theme.GameChoiceTheme
import javax.sql.PooledConnection

class MainActivity : ComponentActivity() {
    private lateinit var gameViewModel: GameViewModel
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var poolViewModel: PoolViewModel
    private lateinit var player: Player
    private lateinit var gameChoiceService: GameChoiceService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            Log.i("GameChoiceService", "Connecting Service")
            val binder = service as GameChoiceService.MyServiceBinder
            gameChoiceService = binder.getService()
            gameChoiceService.doSomething()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        Intent(this, GameChoiceService::class.java).also {
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = Player()
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        poolViewModel = ViewModelProvider(this).get(PoolViewModel::class.java)

        Log.i("Service", "Calling getGames")
        setContent {
            val navController = rememberNavController()

            GameChoiceTheme {
                NavHost(
                    navController = navController,
                    startDestination = "poolcompose"
                ) {
                    composable("simpleoutlinedtextfieldsample") {
                        SimpleOutlinedTextFieldSample(navController, player, authViewModel, playerViewModel)
                    }
                    composable("gameselectioncard") {
                        GameSelectionCard(
                            gameViewModel,
                            navController,
                            player)
                    }
                    composable("playerresultlist") {
                        PlayerResultList(
                            playerViewModel,
                            navController,
                            gameViewModel
                        )
                    }
                    composable("pointcomposable") {
                        PointComposable(navController = navController,
                            player = player,
                        playerViewModel = playerViewModel,
                        gameViewModel = gameViewModel)
                    }

                    composable("wins") {
                        Wins(navController = navController)
                    }

                    composable("poolcompose") {
                        PoolCompose(navController = navController,
                            poolViewModel = poolViewModel, playerViewModel = playerViewModel )
                    }
                }
            }
//            }
        }
//        runCode()

    }

    private fun runService() {
        gameChoiceService.doSomething()
    }

//    private fun runCode() {
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//
////        val workRequest = OneTimeWorkRequestBuilder<GameWorker>()
////            .setConstraints(constraints)
////
////            .build()
//
//       val workManager =  WorkManager.getInstance(applicationContext)
//        workManager.enqueue(workRequest)
//        workManager.getWorkInfoByIdLiveData(workRequest.id)
//            .observe(this) {
//                when (it.state) {
//                    WorkInfo.State.SUCCEEDED -> {
//                        Log.i("Worker", "SUCCEEDED")
//                        Toast.makeText(applicationContext, "WorkFinished", Toast.LENGTH_LONG).show()
//                    }
//                    WorkInfo.State.RUNNING -> {
//                        val progress = it.progress.getString(MESSAGE_KEY)
//                        if (progress != null) {
//                            Log.i("Worker", progress)
//                        }
//                    }
//                    WorkInfo.State.FAILED -> {
//                        Log.i("Worker", "FAILED")
//                    }
//                    else -> {
//                        Log.i("Worker", it.state.name)
//                    }
//                }
//            }
//    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameChoiceTheme {
    }
}

//data class Game(val teamOne: String, val teamTwo: String)




