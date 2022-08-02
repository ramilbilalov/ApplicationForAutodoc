package com.bilalov.testapplicationforappricot.view.additionsView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bilalov.testapplicationforappricot.R


@Composable
fun OctoCatBackground(
    isLoading: Boolean,
    content: @Composable () -> Unit,
) = if (isLoading
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.octocat))
    val progress by animateLottieCompositionAsState(composition)
    Box(
        Modifier
            .fillMaxSize(),
        Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize(),
        )
    }
} else {
    content()
}