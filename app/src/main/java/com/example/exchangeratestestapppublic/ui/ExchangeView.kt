package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.ExchangeViewModel
import com.example.exchangeratestestapppublic.Ordering
import com.example.exchangeratestestapppublic.Screen

@Composable
fun ExchangeView(viewModel: ExchangeViewModel) {

    val mainScreenState = viewModel.mainScreen.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(
                items = mainScreenState.currenciesList,
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
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 83.dp)
                .fillMaxSize(),
            elevation = 4.dp
        ) {
            when (mainScreenState.activeScreen) {
                Screen.POPULAR -> PopularRatesScreen(
                    viewModel = viewModel,
                    currencyRates = mainScreenState.currencyRates
                )
                Screen.FAVORITE -> FavoriteRatesScreen(favoriteCurrencyRates = mainScreenState.favoritesRates)
            }
        }

    }
}
