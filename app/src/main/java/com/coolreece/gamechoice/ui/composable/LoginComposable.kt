package com.coolreece.gamechoice.ui.composable

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolreece.gamechoice.MainActivity
import com.coolreece.gamechoice.data.Player

@Composable
@ExperimentalMaterialApi
fun SimpleOutlinedTextFieldSample(navController: NavController, player: Player) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var entryCode by rememberSaveable { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp,
                    vertical = 8.dp)
                .fillMaxWidth(),
            singleLine = true,
            value = name,
            onValueChange = {
                name = it
                player.name = name
                            },
            label = { Text("Name") },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp,
                    vertical = 8.dp)
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

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp,
                    vertical = 8.dp)
                .fillMaxWidth(),
            value = entryCode,
            onValueChange = { entryCode = it },
            label = { Text("Entry Code") },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp,
                    vertical = 8.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            onClick = {
                if(entryCode == "1234") {
                    Log.i("ButtonLog", "Successful entry")
                    navController.navigate("gameselectioncard")
                }
            }) {
            if (entryCode.isEmpty()) {
                Text("Play")
            }else if (entryCode != "1234") {
                Text("Wrong Entry")
            }else {
                Text("Play")
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