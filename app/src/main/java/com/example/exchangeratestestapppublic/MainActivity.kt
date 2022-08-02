package com.example.exchangeratestestapppublic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import com.example.exchangeratestestapppublic.ui.ExchangeView
import com.example.exchangeratestestapppublic.ui.theme.ExchangeRatesTestAppPublicTheme


data class PopularScreenState(
    val activeScreen: Screen = Screen.POPULAR,
    val chosenCurrency: String? = null,
    val currencyRates: List<CurrencyRatesModel> = emptyList(),
    val currenciesList: List<CurrenciesModel> = emptyList(),
    val ordering: Ordering = Ordering.QUOTE_ASC,
)

class MainActivity : ComponentActivity() {

    private val viewModel: ExchangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExchangeView(viewModel = viewModel)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExchangeRatesTestAppPublicTheme {
    }
}