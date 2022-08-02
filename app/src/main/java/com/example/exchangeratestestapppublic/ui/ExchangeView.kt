package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.ExchangeViewModel
import com.example.exchangeratestestapppublic.Ordering
import com.example.exchangeratestestapppublic.Screen

@Composable
fun ExchangeView(viewModel: ExchangeViewModel) {

    val mainScreenState = viewModel.mainScreen.collectAsState().value

    val currencyRates by viewModel.getCurrencyRates(
        base = mainScreenState.chosenCurrency
    ).collectAsState(initial = emptyList())

    val favoriteCurrencyRates by viewModel.getFavoriteCurrencyRates(
        base = "USD"
    ).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopBar(
                currenciesList,
                onClick = { viewModel.changeChosenCurrency(it) },
                onSortDescRateClick = { viewModel.changeOrder(Ordering.RATE_DESC) },
                onSortAscRateClick = { viewModel.changeOrder(Ordering.RATE_ASC) },
                onSortAscQuoteClick = { viewModel.changeOrder(Ordering.QUOTE_ASC) },
                onSortDescQuoteClick = { viewModel.changeOrder(Ordering.QUOTE_DESC) })
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
