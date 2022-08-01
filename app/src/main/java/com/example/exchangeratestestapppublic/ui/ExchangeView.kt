package com.example.exchangeratestestapppublic.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
    val quotes by viewModel.quotes("USD").collectAsState()

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
        when (mainScreenState.activeScreen) {
            Screen.POPULAR -> PopularRatesScreen(
                currencyNames = quotes,
                currencyRates = emptyList()
            )
            Screen.FAVORITE -> Text(text = "Favorite")
        }
    }
}
