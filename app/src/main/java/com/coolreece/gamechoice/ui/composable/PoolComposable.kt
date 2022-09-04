package com.coolreece.gamechoice.ui.composable

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coolreece.gamechoice.R
import com.coolreece.gamechoice.data.pool.Pool
import com.coolreece.gamechoice.ui.player.PlayerViewModel
import com.coolreece.gamechoice.ui.pool.PoolViewModel

@Composable
fun PoolCompose(poolViewModel: PoolViewModel, navController: NavController,
                playerViewModel: PlayerViewModel) {

    val pools = poolViewModel.poolData.observeAsState()

    pools.value.let {
        Column {
            Log.i("League", "Pools: ${pools.value}")
            TopAppBar(
                title = { Text("Football Pool's ") },
                actions = {

                    IconButton(onClick = {
                        navController.navigate("pointcomposable")
                    }) {
                        Icon(painter = painterResource(R.drawable.ic_baseline_group_add_24),
                            contentDescription = "Add New League")
                    }

                    IconButton(onClick = {
                        navController.navigate("simpleoutlinedtextfieldsample")
                    }) {
                        Icon(painter = painterResource(R.drawable.ic_baseline_login_24),
                            contentDescription = "Add New League",
                          )
                    }
                }
            )


            LazyColumn {
                items(pools.value!!) { pool ->
                    PoolCard(pool = pool, navController, playerViewModel)
                }
            }
        }
    }
}

@Composable
fun PoolCard(pool: Pool, navController: NavController, playerViewModel: PlayerViewModel) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Column() {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                .clickable {
                    playerViewModel.getPlayers("The Unit")
                    navController.navigate("playerresultlist")
                }) {
                Text(
                    text = "${pool.name}",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "${pool.players.size} Players | ${isLeaguePrivate(pool.private)}",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )

                Text(
                    text = "The best league in the world.  Who wants to play?  ",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
            }
//            Row() {
//                TextButton(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier.padding(end = 8.dp, start = 8.dp)
//                ) {
//                    Text(text = "JOIN")
//
//                }
//
//                TextButton(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier.padding(end = 8.dp, start = 8.dp)
//                ) {
//                    Text(text = "View")
//
//                }
//            }

        }
    }

}
private fun isLeaguePrivate(private: Boolean): String {
    return if (private) {
        "PRIVATE"
    } else {
        "PUBLIC"
    }
}