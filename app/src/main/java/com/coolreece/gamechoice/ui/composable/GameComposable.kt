package com.coolreece.gamechoice.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.coolreece.gamechoice.data.Player
import com.coolreece.gamechoice.ui.game.GameViewModel
import com.coolreece.gamechoice.ui.player.PlayerViewModel

@ExperimentalMaterialApi
@Composable
fun GameSelectionCard(
    viewModel: GameViewModel,
    navController: NavController,
    player: Player,
    showDoneIcon: MutableState<Boolean>,
    playerViewModel: PlayerViewModel
) {


    Column {
        Log.i("Player", "Teams: ${player.teams}")
        TopAppBar(
            title = { Text("GameChoice") },
            actions = {

                IconButton(onClick = {
                    if(player.teams.size > 10) {
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
        PickCardList(viewModel, player)
    }
}

@ExperimentalMaterialApi
@Composable
fun PickCardList(
    viewModel: GameViewModel,
    player: Player) {
    LazyColumn() {
        items(viewModel.gameData.value) { game ->
            PickCard(
                game = game,
                player = player)
        }
    }
}
