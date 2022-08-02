package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.ExchangeViewModel
import com.example.exchangeratestestapppublic.R
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel

@Composable
fun PopularRatesScreen(
    currencyRates: List<CurrencyRatesModel>,
    viewModel: ExchangeViewModel
) {

    Column(Modifier.padding(16.dp)) {
        if (currencyRates.isEmpty()) {
            Text(text = "Empty list")
        } else {
            currencyRates.forEach { currencyRatesModel ->
                val isFavorite = currencyRatesModel.isQuoteFavorite
                val icon = if (isFavorite) painterResource(id = R.drawable.ic_baseline_star_checked)
                else painterResource(id = R.drawable.ic_baseline_star_border_unchecked)
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
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "=", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = currencyRatesModel.rate.toString(),
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.clickable {
                            viewModel.changeQuoteFavorite(
                                isFavorite = !isFavorite,
                                quote = currencyRatesModel.quote
                            )
                        },
                        painter = icon,
                        contentDescription = ""
                    )
                }
            }
        }
    }

}
