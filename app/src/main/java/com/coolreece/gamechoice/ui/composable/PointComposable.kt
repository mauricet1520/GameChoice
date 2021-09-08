package com.coolreece.gamechoice.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coolreece.gamechoice.data.Player
import com.coolreece.gamechoice.ui.game.GameViewModel
import com.coolreece.gamechoice.ui.player.PlayerViewModel

@Composable
fun PointComposable(
    navController: NavController,
    playerViewModel: PlayerViewModel = viewModel(),
    player: Player,
    gameViewModel: GameViewModel
) {

    val games = gameViewModel.gameData.value
    var points by remember { mutableStateOf("") }

    val mondayNightGame = games?.filter { it.mondayNight == true }

    val pointsHint = "Total Points for ${mondayNightGame?.get(0)?.home} VS ${mondayNightGame?.get(0)?.away}"
    Column {
        Log.i("Player", "Teams: ${player.teams}")
        TopAppBar(
            title = { Text("GameChoice") },
            actions = {

                IconButton(onClick = {
                    if (player.teams.size > 10 && player.points != 0) {
                        player.week = "week 1"
                        playerViewModel.addPlayer(player)
                        navController.navigate("playerresultlist")
                    }
                }) {
                    Row {
                        Text(text = "Done")
                        Icon(Icons.Filled.Done, contentDescription = "Completed")
                    }
                }

            }
        )

        Text(
            modifier = Modifier.padding(4.dp),
            text = pointsHint,
            style = TextStyle(color = MaterialTheme.colors.primary)
        )
        OutlinedTextField(
            singleLine = true,
            value = points,
            onValueChange = {
                points = it
                player.points = points.toInt()
            },
            label = { Text("Points") },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}