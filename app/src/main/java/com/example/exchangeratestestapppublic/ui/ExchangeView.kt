package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
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

    val currencyRatesSortedByAscQuote by viewModel.getCurrencyRatesSortedByAscQuote(
        base = "USD"
    ).collectAsState(initial = emptyList())

    val currencyRatesSortedByDescQuote by viewModel.getCurrencyRatesSortedByDescQuote(
        base = "USD"
    ).collectAsState(initial = emptyList())

    val currencyRatesSortedByAscRate by viewModel.getCurrencyRatesSortedByAscRate(
        base = "USD"
    ).collectAsState(initial = emptyList())

    val currencyRatesSortedByDescRate by viewModel.getCurrencyRatesSortedByDescRate(
        base = "USD"
    ).collectAsState(initial = emptyList())

    val favoriteCurrencyRates by viewModel.getFavoriteCurrencyRates(
        base = "USD"
    ).collectAsState(initial = emptyList())
//    val currenciesList by viewModel.currenciesList().collectAsState()
//    Log.d("MY_TAG", "currenciesList = $currenciesList")
//    val quotes by viewModel.quotes(chosenCurrency).collectAsState()
    val currenciesList by viewModel.currenciesList.collectAsState()
    Scaffold(
        topBar = {
            TopBar(
                currenciesList,
                onClick = { chosenCurrency = it },
                onSortDescRateClick = {},
                onSortAscRateClick = {},
                onSortAscQuoteClick = {},
                onSortDescQuoteClick = {})
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
                    viewModel = viewModel,
                    currencyRates = currencyRates
                )
                Screen.FAVORITE -> FavoriteRatesScreen(favoriteCurrencyRates = favoriteCurrencyRates)
            }
        }

    }
}
