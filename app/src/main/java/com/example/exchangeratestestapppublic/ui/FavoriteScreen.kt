package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel

@Composable
fun FavoriteRatesScreen(
    favoriteCurrencyRates: List<CurrencyRatesModel>,
) {
    Column(Modifier.padding(16.dp)) {
        favoriteCurrencyRates.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = it.quote, style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "=", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.width(16.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = it.rate.toString(), style = MaterialTheme.typography.h5)
            }
        }
    }
}