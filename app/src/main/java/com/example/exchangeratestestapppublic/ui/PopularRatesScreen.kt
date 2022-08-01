package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.exchangeratestestapppublic.R

@Composable
fun PopularRatesScreen(currencyNames: List<String>?, currencyRates: List<Double>?) {
    Row(Modifier.verticalScroll(rememberScrollState())) {
        Row() {

        }
        Column {
            currencyNames?.forEach {
                Text(text = it)
            }
        }
        Column {
            currencyRates?.forEach {
                Text(text = it.toString())
            }
        }
        Column {
            currencyNames?.forEach {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_star_border_unchecked),
                    contentDescription = ""
                )
            }
        }
    }
}
