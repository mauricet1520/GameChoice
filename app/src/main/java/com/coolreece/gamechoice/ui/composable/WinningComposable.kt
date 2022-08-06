package com.coolreece.gamechoice.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coolreece.gamechoice.data.win.WinningTeam
import com.coolreece.gamechoice.ui.winning.WinningViewModel

@Composable
@ExperimentalMaterialApi
fun Wins(
    navController: NavController,
    winningViewModel: WinningViewModel = viewModel()
) {

    var winningTeams by remember { mutableStateOf(WinningTeam()) }
    var team by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(all = 16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 4.dp
                )
                .fillMaxWidth(),
            singleLine = true,
            value = team,
            onValueChange = {
                team = it
            },
            label = { Text("Winning Team") },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Button(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                winningTeams.teams.add(team)
            }) {
            Text("Add Team")
        }

        Button(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                winningTeams.week = "week 1"
                winningViewModel.addWins(winningTeams)
                navController.navigate("playerresultlist")
            }) {
            Text("Send")
        }

        Text(text = winningTeams.teams.toString(),
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        )
    }

}