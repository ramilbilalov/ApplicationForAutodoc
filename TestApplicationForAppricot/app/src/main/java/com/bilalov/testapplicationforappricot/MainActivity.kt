package com.bilalov.testapplicationforappricot

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.bilalov.testapplicationforappricot.data.module.viewModel.MainViewModel
import com.bilalov.testapplicationforappricot.navigation.Navigation
import com.bilalov.testapplicationforappricot.navigation.Screen
import com.bilalov.testapplicationforappricot.ui.theme.TestApplicationForAppricotTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val navController = rememberNavController()
            TestApplicationForAppricotTheme {
                Scaffold(
                    contentColor = Color.Gray,
                    bottomBar = {
                        BottomAppBar(
                            cutoutShape = MaterialTheme.shapes.small.copy(
                                CornerSize(percent = 50)
                            ),
                            elevation = 20.dp,
                            modifier = Modifier.border(1.dp, Color.Black)
                        )
                        {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_baseline_manage_search_24),
                                    contentDescription = "ConnectionIsLost",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            navController.navigate(
                                                Screen.Main.screenName
                                            )
                                        }
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.ic_outline_info_24),
                                    contentDescription = "ConnectionIsLost",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            if (com.bilalov.testapplicationforappricot.BuildConfig.canOpen == "true") {
                                                navController.navigate(
                                                    Screen.InfoView.screenName
                                                )
                                            } else {
                                                Toast.makeText(
                                                    application,
                                                    "Don't have access to this action...",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                )
                            }
                        }
                    },
                ) {
                    Navigation(
                        navController,
                        context = application,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


