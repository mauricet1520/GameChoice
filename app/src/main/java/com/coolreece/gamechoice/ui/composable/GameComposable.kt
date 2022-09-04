package com.coolreece.gamechoice.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coolreece.gamechoice.data.game.Game
import com.coolreece.gamechoice.data.player.Player
import com.coolreece.gamechoice.ui.game.GameViewModel

@ExperimentalMaterialApi
@Composable
fun GameSelectionCard(
    gameViewModel: GameViewModel = viewModel(),
    navController: NavController,
    player: Player
) {

    val games = gameViewModel.gameData.observeAsState()
    games.value.let {
        Column {
            Log.i("Player", "Teams: ${player.teams}")
            TopAppBar(
                title = { Text("GameChoice") },
                actions = {

                    IconButton(onClick = {
                        if (player.teams.size > 15) {
                            navController.navigate("pointcomposable")
                        }
                    }) {
                        Row {

                            Text(text = "Done")
                            Icon(Icons.Filled.Done, contentDescription = "Completed")
                        }
                    }

                }
            )
            PickCardList(it!!, player)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun PickCardList(
    games: List<Game>,
    player: Player
) {

    LazyColumn() {
        items(games) { game ->
            PickCard(
                game = game,
                player = player)
        }
    }

}
