package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.R
import com.example.exchangeratestestapppublic.domain.model.RatesModel
import com.example.exchangeratestestapppublic.domain.model.Symbol

@Composable
fun FavoriteRatesScreen(
    state: MainScreenState
) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.favoritesRates.isEmpty() || state.chosenCurrency == null) {
            Text(
                text = stringResource(id = R.string.favorite_screen_title),
                style = MaterialTheme.typography.h5,
                color = Color.Gray
            )
        } else {
            state.favoritesRates.forEach { currencyRatesModel ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = currencyRatesModel.base.value, style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "/", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = currencyRatesModel.quote.value, style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = currencyRatesModel.rate.toString(),
                        style = MaterialTheme.typography.h5
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Divider()
                Spacer(modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteRatesScreenPreview() {
    FavoriteRatesScreen(
        state = MainScreenState(
            chosenCurrency = Symbol.USD,
            favoritesRates = listOf(
                RatesModel(
                    base = Symbol.USD,
                    quote = Symbol.EUR,
                    rate = 0.85,
                    timestamp = 0,
                    isQuoteFavorite = true,
                ),
                RatesModel(
                    base = Symbol.USD,
                    quote = Symbol.GBP,
                    rate = 0.75,
                    timestamp = 0,
                    isQuoteFavorite = true,
                )
            )
        ),
    )
}