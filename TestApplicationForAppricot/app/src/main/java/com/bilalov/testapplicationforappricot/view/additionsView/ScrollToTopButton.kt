package com.bilalov.testapplicationforappricot.view.additionsView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bilalov.testapplicationforappricot.R
import com.bilalov.testapplicationforappricot.ui.theme.Shapes
import com.bilalov.testapplicationforappricot.ui.theme.TestApplicationForAppricotTheme

@Composable
fun ScrollToTopButton(onClick: () -> Unit) {
    TestApplicationForAppricotTheme {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scroll_to_top))
        val progress by animateLottieCompositionAsState(composition)
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 45.dp)
                .alpha(0.6f),
            Alignment.BottomEnd
        ) {
            Button(
                onClick = { onClick() }, modifier = Modifier
                    .shadow(12.dp, shape = Shapes.large)
                    .clip(shape = Shapes.large)
                    .size(64.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}