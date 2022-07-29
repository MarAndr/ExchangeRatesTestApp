package com.example.exchangeratestestapppublic

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.exchangeratestestapppublic.ui.theme.ExchangeRatesTestAppPublicTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ExchangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.getLatestCurrency()
            val state = viewModel.stateCurrency.collectAsState().value
            Log.d("MY_TAG", "$state")
            ExchangeRatesTestAppPublicTheme {
//                LazyColumn(content = {
//                    items(state){
//                        Text(text = it.name)
//                    }
//                })
            }
        }
    }
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