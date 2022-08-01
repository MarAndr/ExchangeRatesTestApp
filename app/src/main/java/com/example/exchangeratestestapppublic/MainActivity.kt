package com.example.exchangeratestestapppublic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.exchangeratestestapppublic.ui.ExchangeView
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