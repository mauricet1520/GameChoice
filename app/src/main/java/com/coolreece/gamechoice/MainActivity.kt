package com.coolreece.gamechoice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coolreece.gamechoice.data.Player
import com.coolreece.gamechoice.ui.composable.*
import com.coolreece.gamechoice.ui.game.GameViewModel
import com.coolreece.gamechoice.ui.player.PlayerViewModel
import com.coolreece.gamechoice.ui.theme.GameChoiceTheme

class MainActivity : ComponentActivity() {
    private lateinit var gameViewModel: GameViewModel
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var player: Player


    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = Player()
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        Log.i("Service", "Calling getGames")
        gameViewModel.getGames()
        setContent {
            val navController = rememberNavController()

            // A surface container using the 'background' color from the theme
//            Surface(color = MaterialTheme.colors.background) {
            GameChoiceTheme {
                val showDoneIcon = remember { mutableStateOf(false) }

                NavHost(
                    navController = navController,
                    startDestination = "playerresultlist"
                ) {
                    composable("simpleoutlinedtextfieldsample") {
                        SimpleOutlinedTextFieldSample(navController, player)
                    }
                    composable("gameselectioncard") {
                        GameSelectionCard(
                            gameViewModel,
                            navController,
                            player,
                            showDoneIcon,
                            playerViewModel = viewModel()
                        )
                    }
                    composable("playerresultlist") {
                        PlayerResultList(
                            playerViewModel,
                                    navController
                        )
                    }
                }
            }
//            }
        }

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




