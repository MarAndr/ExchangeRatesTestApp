package com.example.exchangeratestestapppublic.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.exchangeratestestapppublic.ui.theme.ExchangeRatesTestAppPublicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ExchangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainScreenState = viewModel.mainScreen.collectAsState().value

            ExchangeRatesTestAppPublicTheme {
                ExchangeView(mainScreenState, viewModel = viewModel)
            }
        }

    }
}
