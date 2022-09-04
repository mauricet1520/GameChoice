package com.coolreece.gamechoice.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolreece.gamechoice.data.auth.AuthUser
import com.coolreece.gamechoice.data.player.Player
import com.coolreece.gamechoice.ui.auth.AuthViewModel
import com.coolreece.gamechoice.ui.player.PlayerViewModel

@Composable
@ExperimentalMaterialApi
fun SimpleOutlinedTextFieldSample(
    navController: NavController,
    player: Player,
    authViewModel: AuthViewModel,
    playerViewModel: PlayerViewModel
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    authViewModel.emailData.observeAsState()

    var entryCode by rememberSaveable { mutableStateOf("") }

    var nameTaken by rememberSaveable { mutableStateOf(false) }

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
            value = name,
            onValueChange = {
                name = it
                player.name = name
                nameTaken = false
            },
            label = { Text("Name") },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 4.dp
                )
                .fillMaxWidth(),
            singleLine = true,
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text("Password") },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        OutlinedTextField(
            modifier = Modifier

                .padding(
                    horizontal = 4.dp,
                    vertical = 4.dp
                )
                .fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                player.email = email
            },
            label = { Text("Email") },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

//        OutlinedTextField(
//            modifier = Modifier
//                .padding(
//                    horizontal = 4.dp,
//                    vertical = 4.dp
//                )
//                .fillMaxWidth(),
//            value = entryCode,
//            onValueChange = { entryCode = it },
//            label = { Text("Entry Code") },
//            textStyle = TextStyle(color = MaterialTheme.colors.primary),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    authViewModel.signIn(
                        AuthUser(
                            email = email,
                            password = password,
                            true
                        )
                    )
                    authViewModel.emailData.value.let {
                        navController.navigate("gameselectioncard")


                    }
//                    playerViewModel.playerData.value?.forEach {
//                        if (player.name == it.name) {
//                            nameTaken = true
//                        }
//                    }
//                    if (!nameTaken) {
//                        Log.i("ButtonLog", "Successful entry")
//                        navController.navigate("gameselectioncard")
//                    }
//                    if (entryCode == "9876") {
//                        navController.navigate("wins")
//                    }

                }) {

                Text("Sign In")
//                when {
//                    entryCode.isEmpty() -> Text("NEXT")
//                    entryCode != "1234" -> Text("Wrong Entry")
//                    nameTaken -> Text("Name Already Taken")
//                    else -> Text("Play")
//                }
            }
        }
    }
}

private fun validateCredentials(email: String, password: String): Boolean {
    if (email == "mauricet1520@gmail.com" && password == "1234test") {
        return true
    }
    return false
}