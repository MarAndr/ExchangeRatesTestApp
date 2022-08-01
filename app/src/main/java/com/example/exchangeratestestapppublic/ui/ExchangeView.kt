package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.ExchangeViewModel
import com.example.exchangeratestestapppublic.Screen

@Composable
fun ExchangeView(viewModel: ExchangeViewModel) {
    var chosenCurrency: String? by remember {
        mutableStateOf(null)
    }
    val mainScreenState = viewModel.mainScreen.collectAsState().value

    val currencyRates by viewModel.getCurrencyRates(
        base = "USD"
    ).collectAsState(initial = emptyList())
    val quotes by viewModel.quotes(chosenCurrency).collectAsState()

    Scaffold(
        topBar = {
            TopBar(quotes) {
                chosenCurrency = it
            }
        },
        bottomBar = {
            BottomBar(viewModel = viewModel, state = mainScreenState)
        }
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = 4.dp
        ) {
            when (mainScreenState.activeScreen) {
                Screen.POPULAR -> PopularRatesScreen(
                    currencyRates = currencyRates
                )
                Screen.FAVORITE -> Text(text = "Favorite")
            }
        }

    }
}
