package com.bilalov.testapplicationforappricot.view

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bilalov.testapplicationforappricot.connectionChecker.ConnectionCheckerImpl
import com.bilalov.testapplicationforappricot.data.dataRow.RowProfile
import com.bilalov.testapplicationforappricot.data.module.viewModel.MainViewModel
import com.bilalov.testapplicationforappricot.ui.theme.TestApplicationForAppricotTheme
import com.bilalov.testapplicationforappricot.view.additionsView.MyRow
import com.bilalov.testapplicationforappricot.view.additionsView.OctoCatBackground
import com.bilalov.testapplicationforappricot.view.additionsView.ScrollToTopButton
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


private val connection = ConnectionCheckerImpl

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FlowOperatorInvokedInComposition",
    "CoroutineCreationDuringComposition"
)
@Composable
fun DefaultPreview(
    viewModel: MainViewModel,
    context: Application,
    navController: NavHostController,
) {


    var isLoading by remember { mutableStateOf(false) }
    var isLoadingAnimOcto by remember {
        mutableStateOf(true)
    }

    TestApplicationForAppricotTheme {
        var showButtonChecker by rememberSaveable { mutableStateOf(false) }
        var textInputSearch by remember { mutableStateOf("") }

        val listState = rememberLazyListState()


        var checkEqualsSearch by remember { mutableStateOf("") }
        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemScrollOffset > 0
            }
        }
        val coroutineScope = rememberCoroutineScope()

        viewModel.statusApi.observeForever{
            isLoadingAnimOcto = false
            isLoading = false
        }
        //Реализация с помощью  Flow
        // val listItem by viewModel.sharedFlow.collectAsState(null)

        //Реализация liveData (мне больше импонирует)
        val listItem by viewModel.allSearchItems.observeAsState()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress { _, dragAmount ->
                    }
                }
        ) {
            LaunchedEffect(showButtonChecker) {
                if (showButtonChecker) {
                    delay(1000)
                    if (connection.isOnline(context)) {
                        Toast.makeText(context, "Connection is good", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(context, "Error, connection is lost", Toast.LENGTH_LONG)
                            .show()
                    }
                    showButtonChecker = false
                }
            }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = showButtonChecker),
                onRefresh = {
                    showButtonChecker = true
                },
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(0.dp, 4.dp, 4.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val focusManager = LocalFocusManager.current
                        OutlinedTextField(
                            value = textInputSearch,
                            onValueChange = { newText ->
                                textInputSearch = newText.trimStart { it == '0' }
                            },
                            singleLine = true,
                            label = {
                                Text("Search")
                            },
                            modifier = Modifier
                                .padding(8.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.Sentences,
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                if (checkEqualsSearch != textInputSearch) {
                                    isLoading = true
                                    viewModel.getRepository(textInputSearch, context)
                                    checkEqualsSearch = textInputSearch
                                }
                                focusManager.clearFocus()
                            }),
                        )
                        IconButton(
                            onClick = {
                                if (checkEqualsSearch != textInputSearch) {
                                    isLoading = true
                                    viewModel.getRepository(textInputSearch, context)
                                    checkEqualsSearch = textInputSearch
                                }
                            }, modifier = Modifier
                                .size(56.dp)
                        ) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Refresh Button",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    LoaderView(isLoading = isLoading) {}

                    OctoCatBackground(isLoading = isLoadingAnimOcto) {}

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight(4f)
                            .padding(bottom = 55.dp, top = 3.dp), state = listState
                    ) {
                        items(count = 1) {
                            var i = 0
                            val size = listItem?.items?.size.toString()
                            if (listItem?.items?.isEmpty() == false) {
                                while (i < size.toInt()) { //Заполненение UI-списка
                                    MyRow(
                                        item = RowProfile(
                                            name = listItem?.items?.get(i)?.name.toString(),
                                            description = listItem?.items?.get(i)?.description.toString(),
                                            login = listItem?.items?.get(i)?.owner?.login.toString(),
                                            language = listItem?.items?.get(i)?.language.toString(),
                                            updated_at = listItem?.items?.get(i)?.updated_at.toString(),
                                            stargazers_count = listItem?.items?.get(i)?.stargazers_count.toString(),
                                            avatar_url = listItem?.items?.get(i)?.owner?.avatar_url.toString(),
                                            navController = navController,
                                            context = context,
                                            viewModel = viewModel
                                        )
                                    )
                                    i++
                                }
                            } else {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 45.dp),
                                    Alignment.Center
                                ) {
                                    Text(
                                        text = "Not found",
                                        modifier = Modifier
                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    visible = showButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ScrollToTopButton(onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    })
                }
            }

        }

    }
}