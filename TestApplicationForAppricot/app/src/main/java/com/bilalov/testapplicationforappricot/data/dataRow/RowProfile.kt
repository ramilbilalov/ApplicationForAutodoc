package com.bilalov.testapplicationforappricot.data.dataRow

import android.app.Application
import androidx.navigation.NavHostController
import com.bilalov.testapplicationforappricot.data.module.viewModel.MainViewModel

class RowProfile(
    var name: String,
    var description: String,
    var login: String,
    var language: String,
    var updated_at: String,
    var stargazers_count: String,
    var avatar_url: String,
    var navController: NavHostController,
    var context: Application?,
    var viewModel: MainViewModel
)