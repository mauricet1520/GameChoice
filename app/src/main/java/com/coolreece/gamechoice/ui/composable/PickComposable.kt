package com.coolreece.gamechoice.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.coolreece.gamechoice.data.game.Game
import com.coolreece.gamechoice.data.player.Player

@ExperimentalMaterialApi
@Composable
fun PickCard(game: Game, player: Player) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        val radioOptions = listOf(game.home, game.away, "")
        val showTeamIcon = rememberSaveable { mutableStateOf(false) }
        val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf(radioOptions[2]) }
        Column(Modifier.selectableGroup()) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                radioOptions.forEach lit@{ text ->
                    if (text.isEmpty()) return@lit
                    Row(
                        Modifier
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    player.teams.remove(game.away)
                                    player.teams.remove(game.home)
                                    player.teams.add(text)
                                    onOptionSelected(text)
                                    showTeamIcon.value = true
                                },
                                role = Role.RadioButton
                            )
                            .padding(
                                horizontal = 16.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(all = 16.dp
                            )
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
//                    game.mondayNight
//                    if(game.mondayNight)
                    Text(
                        text = selectedOption,
                        modifier = Modifier.padding(all = 16.dp)
                    )
                }
                if (showTeamIcon.value) Icon(Icons.Filled.Done, contentDescription = "Completed")

            }

        }
    }
}
