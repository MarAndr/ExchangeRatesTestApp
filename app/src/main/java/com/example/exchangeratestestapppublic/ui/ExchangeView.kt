package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.R
import com.example.exchangeratestestapppublic.ui.theme.ExchangeRatesTestAppPublicTheme
import kotlinx.coroutines.launch

@Composable
fun ExchangeView(state: MainScreenState, viewModel: ExchangeViewModel?) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val unknownErrorText = stringResource(id = R.string.unknown_error)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                modifier = Modifier,
                state = state,
                items = state.currenciesList,
                onClick = {
                    viewModel?.changeChosenCurrency(it)
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = if (state.error != null) {
                                state.error.message
                                    ?: unknownErrorText
                            } else {
                                "Вы выбрали ${it.name} в качестве базовой валюты"
                            }
                        )
                    }
                },
                onSortDescRateClick = { viewModel?.changeOrder(Ordering.RATE_DESC) },
                onSortAscRateClick = { viewModel?.changeOrder(Ordering.RATE_ASC) },
                onSortAscQuoteClick = { viewModel?.changeOrder(Ordering.QUOTE_ASC) },
                onSortDescQuoteClick = { viewModel?.changeOrder(Ordering.QUOTE_DESC) })
        },
        bottomBar = {
            BottomBar(viewModel = viewModel, state = state)
        }
    ) {
        CircularProgressBar(isDisplayed = state.isLoading)

        Card(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 83.dp)
                .fillMaxSize(),
            elevation = 4.dp
        ) {
            when (state.activeScreen) {
                Screen.Popular -> PopularRatesScreen(
                    currencyRates = state.currencyRates,
                    viewModel = viewModel,
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
                    favoriteCurrencyRates = state.favoritesRates,
                    state = state
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() = ExchangeRatesTestAppPublicTheme {
    ExchangeView(
        state = MainScreenState(),
        viewModel = null
    )
}