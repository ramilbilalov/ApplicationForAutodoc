package com.bilalov.testapplicationforappricot.view

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
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
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
import com.bilalov.testapplicationforappricot.ui.theme.Shapes

@OptIn(ExperimentalCoilApi::class)
@Composable
fun InfoView() {
    val painterFirst = rememberImagePainter(
        data = "https://sun9-16.userapi.com/impg/mpATu2r6W0uu08q3zyUbgc4bWhVa6yw3BpFQFg/S9JwrndfNVc.jpg?size=1440x1920&quality=95&sign=da534a53ad169e4c3e379ee92cd3ddf2&type=album",
        builder = {
            placeholder(R.drawable.ic_baseline_find_replace_24)
            error(R.drawable.error_img)
            crossfade(1000)
        })
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
                            text = "Developer name: Ramil",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "Bio: I am a very hardworking person, mobile development is a piece of my soul, it is likely that half of my heart belongs to her",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)
                        )
                        Text(
                            text = "Vk: @nesmotrumenya",
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
                                        Icons.Filled.Face,
                                        contentDescription = "Refresh Button",
                                    )
                                    Text(
                                        text = "Position: junior",
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
                                text = "Bilalov.2016@mail.ru",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

            }
            Text(
                text = "Stack in app: Jetpack Compose, MVVM, Retrofit, Hilt, Coil, Coroutines, LiveData/(KotlinFlow), Lottie, Clean Architecture",
                modifier = Modifier
                    .padding(horizontal = 18.dp)
            )
        }
    }
}