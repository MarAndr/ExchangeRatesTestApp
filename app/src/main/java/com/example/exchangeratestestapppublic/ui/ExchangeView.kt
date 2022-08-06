package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.ExchangeViewModel
import com.example.exchangeratestestapppublic.Ordering
import com.example.exchangeratestestapppublic.Screen
import kotlinx.coroutines.launch

@Composable
fun ExchangeView(viewModel: ExchangeViewModel) {
    val mainScreenState = viewModel.mainScreen.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                items = mainScreenState.currenciesList,
                onClick = {
                    viewModel.changeChosenCurrency(it)
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = if (mainScreenState.error != null) {
                                mainScreenState.error.message
                                    ?: "Неизвестная ошибка, повторите позже"
                            } else {
                                "Вы выбрали ${it.name} в качестве базовой валюты"
                            }
                        )
                    }
                },
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
                ) { isFavorite, quote ->
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            if (isFavorite) {
                                "Вы удалили валюту $quote из Избранного"
                            } else {
                                "Вы добавили валюту $quote в Избранное"
                            }
                        )
                    }
                }
                Screen.FAVORITE -> FavoriteRatesScreen(favoriteCurrencyRates = mainScreenState.favoritesRates)
            }
        }

    }
}
