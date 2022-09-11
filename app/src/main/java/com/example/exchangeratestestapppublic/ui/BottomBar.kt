package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.R

@Composable
fun BottomBar(viewModel: ExchangeViewModel?, state: MainScreenState) {
    Surface(elevation = 8.dp, modifier = Modifier.height(75.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (state.activeScreen == Screen.Popular) {
                            MaterialTheme.colors.secondary
                        } else MaterialTheme.colors.background
                    )
                    .fillMaxHeight()
                    .clickable {
                        viewModel?.changeScreen(Screen.Popular)
                    }
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.popular_bottom_menu),
                    style = MaterialTheme.typography.h5
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .background(
                        color = if (state.activeScreen == Screen.Favorite) {
                            MaterialTheme.colors.secondary
                        } else MaterialTheme.colors.background
                    )
                    .fillMaxHeight()
                    .clickable {
                        viewModel?.changeScreen(Screen.Favorite)
                    }
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.favorite_bottom_menu),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}
