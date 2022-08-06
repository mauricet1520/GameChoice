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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.coolreece.gamechoice.data.game.GameWorker
import com.coolreece.gamechoice.data.player.Player
import com.coolreece.gamechoice.ui.composable.*
import com.coolreece.gamechoice.ui.game.GameViewModel
import com.coolreece.gamechoice.ui.player.PlayerViewModel
import com.coolreece.gamechoice.ui.theme.GameChoiceTheme

class MainActivity : ComponentActivity() {
    private lateinit var gameViewModel: GameViewModel
    private lateinit var playerViewModel: PlayerViewModel
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
        Log.i("Service", "Calling getGames")
//        gameViewModel.getGames()
        setContent {
            val navController = rememberNavController()

            // A surface container using the 'background' color from the theme
//            Surface(color = MaterialTheme.colors.background) {
            GameChoiceTheme {
                val showDoneIcon = remember { mutableStateOf(false) }
                val mondayNightGame = remember { mutableStateOf("")}

                NavHost(
                    navController = navController,
                    startDestination = "playerresultlist"
                ) {
                    composable("simpleoutlinedtextfieldsample") {
                        SimpleOutlinedTextFieldSample(navController, player, playerViewModel)
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
                }
            }
//            }
        }

    }

    private fun runService() {
        gameChoiceService.doSomething()
    }

    private fun runCode() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<GameWorker>()
            .setConstraints(constraints)
            .build()
       val workManager =  WorkManager.getInstance(applicationContext)
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val result = it.outputData.getString(DATA_KEY) ?: "null"
                        Log.i("Worker", result)
                        Toast.makeText(applicationContext, "WorkFinished", Toast.LENGTH_LONG).show()
                    }
                    WorkInfo.State.RUNNING -> {
                        val progress = it.progress.getString(MESSAGE_KEY)
                        if(progress != null) {
                            Log.i("Worker", progress)
                        }
                    }
                    else -> {
                        Log.i("Worker", it.state.name)
                    }
                }
            })
    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameChoiceTheme {
    }
}

//data class Game(val teamOne: String, val teamTwo: String)




