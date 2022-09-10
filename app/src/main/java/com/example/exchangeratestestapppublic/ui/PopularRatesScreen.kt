package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.R
import com.example.exchangeratestestapppublic.domain.model.RatesModel

@Composable
fun PopularRatesScreen(
    currencyRates: List<RatesModel>,
    viewModel: ExchangeViewModel,
    onStarClick: (Boolean, String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currencyRates.isEmpty()) {
            Text(
                text = stringResource(id = R.string.popular_screen_title),
                style = MaterialTheme.typography.h5,
                color = Color.Gray
            )
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
                    Text(text = currencyRatesModel.base.toString(), style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "/", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = currencyRatesModel.quote.toString(), style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = currencyRatesModel.rate.toString(),
                            style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        Icon(
                            modifier = Modifier.clickable {
                                viewModel.changeQuoteFavorite(
                                    isFavorite = !isFavorite,
                                    quote = currencyRatesModel.quote.toString()
                                )
                                onStarClick(isFavorite, currencyRatesModel.quote.toString())
                            },
                            painter = icon,
                            contentDescription = ""
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Divider()
                Spacer(modifier = Modifier.padding(top = 8.dp))
            }
        }
    }

}
