package com.bilalov.testapplicationforappricot.view

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bilalov.testapplicationforappricot.R
import com.bilalov.testapplicationforappricot.data.module.viewModel.MainViewModel
import com.bilalov.testapplicationforappricot.ui.theme.Shapes
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition")
@Composable
fun SecondScreen(
    login: String?,
    context: Application?,
    viewModel: MainViewModel,
) {
    val listItems by viewModel.allUserInfo.observeAsState(initial = null)
    var refreshing by rememberSaveable { mutableStateOf(false) }

    val painterFirst = rememberImagePainter(
        data = listItems?.avatar_url.toString(),
        builder = {
            placeholder(R.drawable.ic_baseline_find_replace_24)
            error(R.drawable.error_img)
            crossfade(1000)
        })

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(1000)
            if (context != null) {
                if (login != null) {
                    viewModel.getUserInfo(login, context)
                    if (viewModel.statusApiSecondFuel.value == "true") {
                        Toast.makeText(context, "Connection is good", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(context, "Error, connection is lost", Toast.LENGTH_LONG)
                            .show()
                    }
                    refreshing = false
                }
            }
        }
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
        },
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp, 20.dp),

            ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(57.dp, 50.dp),
                    shape = Shapes.large,
                    elevation = 8.dp,
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterFirst,
                            contentDescription = "cardBackground",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(3.dp)
                                .size(269.dp)
                                .clip(shape = Shapes.large)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Name: $login",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Text(
                                text = "Bio: ${listItems?.bio}",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)
                            )
                            Text(
                                text = "Twitter: ${listItems?.twitter_username}",
                                fontSize = 14.sp
                            )
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                elevation = 5.dp,
                                modifier = Modifier
                                    .padding(8.dp),
                                border = BorderStroke(1.dp, Color.Red)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(4.dp, 4.dp, 4.dp, 4.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically

                                    ) {
                                        Icon(
                                            Icons.Filled.Person,
                                            contentDescription = "Refresh Button",
                                        )
                                        Text(
                                            text = "Followers: ${listItems?.followers}",
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .padding(8.dp)
                                        )
                                    }

                                }

                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(4.dp, 4.dp, 4.dp, 4.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Email,
                                    contentDescription = "Refresh Button",
                                )
                                Text(
                                    text = "${listItems?.email}",
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            Text(
                                text = "${listItems?.blog}",
                                modifier = Modifier.padding(8.dp),
                                color = Color.Blue
                            )
                        }
                    }
                }
            }
        }
    }
}
