package com.bilalov.testapplicationforappricot.view.additionsView

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.bilalov.testapplicationforappricot.data.dataRow.RowProfile
import com.bilalov.testapplicationforappricot.navigation.Screen
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalCoilApi::class)
@Composable
fun MyRow(item: RowProfile) {

    val painterFirst = rememberImagePainter(
        data = item.avatar_url,
        builder = {
            placeholder(R.drawable.ic_baseline_find_replace_24)
            error(R.drawable.error_img)
            crossfade(1000)
        })

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                item.viewModel.getUserInfo(item.login, item.context as Application)
                item.navController.navigate(
                    Screen.SecondView
                        .withArgs(
                            item.login
                        )
                )

            },
        shape = RoundedCornerShape(12.dp),
        elevation = 5.dp,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterFirst,
                contentDescription = "cardBackground",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(3.dp)
                    .size(76.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    text = "Name: ${item.name}",
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Description: ${item.description}",
                    modifier = Modifier.padding(8.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier
                            .padding(0.dp, 4.dp, 4.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = 5.dp,
                            modifier = Modifier
                                .padding(8.dp),
                            border = BorderStroke(1.dp, Color.Red)
                        ) {
                            Text(
                                text = item.language,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                        }
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Refresh Button",
                        )
                        Text(
                            text = item.stargazers_count,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                val isoReformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ROOT)
                val date = item.updated_at.replace("\\+0([0-9])\\:00".toRegex(), "+0$100")

                Text(
                    text = "Update at: ${isoReformat.parse(date)}",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
