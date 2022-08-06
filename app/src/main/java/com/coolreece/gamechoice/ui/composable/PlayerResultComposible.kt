package com.coolreece.gamechoice.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coolreece.gamechoice.data.game.Game
import com.coolreece.gamechoice.data.player.Player
import com.coolreece.gamechoice.ui.game.GameViewModel
import com.coolreece.gamechoice.ui.player.PlayerViewModel

@Composable
fun PlayerResultCard(player: Player, game: Game?) {


    player.teams.forEach { team ->
        if (game?.mondayNight == true ) {
            when(team) {
                game.home -> player.mondayNightTeam = game.home
                game.away -> player.mondayNightTeam = game.away
            }
        }
    }
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
//        Image(
//            painter = painterResource(R.drawable.profile_picture_test),
//            contentDescription = "Contact profile picture",
//            modifier = Modifier
//                // Set image size to 40 dp
//                .size(40.dp)
//                // Clip image to be shaped as a circle
//                .clip(CircleShape)
//                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
//
//        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            val playerName = player.name ?: player.email!!
            val wins = "Wins: ${player.wins}  "
            var winningTeams = "Winning Teams: ${player.winningTeams} \n"
            if (player.winningTeams.isEmpty()) {
                winningTeams = "Winning Teams: None \n"
            }
            val points = "Points: ${player.points}  "
            val teams = "Teams: ${player.teams}"
            val mondayNightGame = "MNF: ${player.mondayNightTeam}  "
            val message = wins + points + mondayNightGame + winningTeams + teams

            Text(
                text = playerName,
                color = MaterialTheme.colors.secondaryVariant
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun PlayerResultList(
    playerViewModel: PlayerViewModel = viewModel(),
    navController: NavController,
    gameViewModel: GameViewModel = viewModel()
) {
//    playerViewModel.getPlayers()

    val players = playerViewModel.playerData.observeAsState()
    val games = gameViewModel.gameData.observeAsState()

    val mondayNightGame = games.value?.filter { it.mondayNight == true }

    Column {
        TopAppBar(
            title = { Text("${players.value?.size} Players") },
            actions = {

                IconButton(onClick = {
                    navController.navigate("playerresultlist")
                }) {
                    Row {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                }

                IconButton(onClick = {
                    navController.navigate("simpleoutlinedtextfieldsample")

                }) {
                    Row {
                        Text(text = "ADD")
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            }
        )

        if(players.value!!.isNotEmpty()) {
            LazyColumn {
                items(players.value!!) { player ->
                    PlayerResultCard(player, mondayNightGame?.get(0))
                }
            }
        }else {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
    }
}