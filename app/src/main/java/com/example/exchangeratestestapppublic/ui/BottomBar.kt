package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.ExchangeViewModel
import com.example.exchangeratestestapppublic.PopularScreenState
import com.example.exchangeratestestapppublic.Screen

@Composable
fun BottomBar(viewModel: ExchangeViewModel, state: PopularScreenState) {
    Surface(elevation = 8.dp, modifier = Modifier.height(75.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (state.activeScreen == Screen.POPULAR) {
                            Color.Blue.copy(alpha = 0.5f)
                        } else Color.White
                    )
                    .fillMaxHeight()
                    .clickable {
                        viewModel.changeScreen(Screen.POPULAR)
                    }
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Популярное", color = Color.Blue)
            }

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .background(
                        color = if (state.activeScreen == Screen.FAVORITE) {
                            Color.Blue.copy(alpha = 0.5f)
                        } else Color.White
                    )
                    .fillMaxHeight()
                    .clickable {
                        viewModel.changeScreen(Screen.FAVORITE)
                    }
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Избранное", color = Color.Blue)
            }
        }
    }
}
