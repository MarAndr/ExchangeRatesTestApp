package com.example.exchangeratestestapppublic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.ui.theme.ExchangeRatesTestAppPublicTheme


data class MainScreenState(
    val activeScreen: Screen = Screen.POPULAR,
    val currencyNames: List<String> = emptyList()
)

class MainActivity : ComponentActivity() {

    private val viewModel: ExchangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Scaffold(
                topBar = {
                    TopAppBar {
                        Text(text = "Choose Currency")
                    }
                },
                bottomBar = {
                    Row {
                        Text(text = "Популярное")
                        Divider(Modifier.height(5.dp))
                        Text(text = "Избранное")
                    }
                }
            ) {

            }

            val state = viewModel.state.collectAsState().value
            val currencyNames = state.rates?.map { (currencyName: String, rate: Double) ->
                currencyName
            }

            val currencyRates = state.rates?.map { (currencyName: String, rate: Double) ->
                rate
            }
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
    }
}

@Composable
fun Test(state: LatestCurrencyResponse) {
    Text(text = state.rates.toString())
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExchangeRatesTestAppPublicTheme {
        Greeting("Android")
    }
}