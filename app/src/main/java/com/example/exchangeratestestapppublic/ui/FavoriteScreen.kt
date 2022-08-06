package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.MainScreenState
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel

@Composable
fun FavoriteRatesScreen(
    favoriteCurrencyRates: List<CurrencyRatesModel>,
    mainScreenState: MainScreenState
) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (favoriteCurrencyRates.isEmpty() || mainScreenState.chosenCurrency == null) {
            Text(
                text = "Список избранного пуст или вы не выбрали базовую валюту",
                style = MaterialTheme.typography.h5,
                color = Color.Gray
            )
        } else {
            favoriteCurrencyRates.forEach { currencyRatesModel ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = currencyRatesModel.base, style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "/", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = currencyRatesModel.quote, style = MaterialTheme.typography.h5)
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