package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.R
import kotlinx.coroutines.launch

@Composable
fun ExchangeView(viewModel: ExchangeViewModel) {
    val mainScreenState = viewModel.mainScreen.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val unknownErrorText = stringResource(id = R.string.unknown_error)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                modifier = Modifier,
                mainScreenState = mainScreenState,
                items = mainScreenState.currenciesList,
                onClick = {
                    viewModel.changeChosenCurrency(it)
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = if (mainScreenState.error != null) {
                                mainScreenState.error.message
                                    ?: unknownErrorText
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
        CircularProgressBar(isDisplayed = mainScreenState.isLoading)

        Card(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 83.dp)
                .fillMaxSize(),
            elevation = 4.dp
        ) {
            when (mainScreenState.activeScreen) {
                Screen.Popular -> PopularRatesScreen(
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
                Screen.Favorite -> FavoriteRatesScreen(
                    favoriteCurrencyRates = mainScreenState.favoritesRates,
                    mainScreenState = mainScreenState
                )
            }
        }

    }
}

@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding()
                    .size(50.dp),
                color = MaterialTheme.colors.primary
            )
        }
    }
}
