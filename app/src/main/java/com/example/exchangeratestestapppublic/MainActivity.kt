package com.example.exchangeratestestapppublic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            
//            val state = viewModel.state.collectAsState().value
//            viewModel.getCurrencyNamesList()
//            val currencyNames =
//                viewModel.currencyNames.collectAsState().value.symbols?.map { (currency: String, currencyFullName: String) ->
//                    "$currencyFullName ($currency)"
//                }
//            val mainScreenState = viewModel.mainScreen.collectAsState().value
//
//
//            val currencyRates = state.rates?.map { (currencyName: String, rate: Double) ->
//                rate
//            }
//            Scaffold(
//                topBar = {
//                    if (currencyNames != null) {
//                        TopBar(currencyNames)
//                    }
//                },
//                bottomBar = {
//                    BottomBar(viewModel = viewModel)
//                }
//            ) {
//                when (mainScreenState.activeScreen) {
//                    Screen.POPULAR -> MainScreen(
//                        currencyNames = currencyNames,
//                        currencyRates = currencyRates
//                    )
//                    Screen.FAVORITE -> Text(text = "Favorite")
//                }
//            }
        }
    }
}


@Composable
fun TopBar(items: List<String>) {
    Surface(elevation = 8.dp, modifier = Modifier.height(75.dp)) {
        DropdownMenu(items)
    }
}


@Composable
fun DropdownMenu(items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    val disabledValue = "B"
    var selectedIndex: Int? by remember { mutableStateOf(null) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        if (selectedIndex != null) {
            Text(
                text = items[selectedIndex!!],
                style = MaterialTheme.typography.h5
            )
        } else {
            Text(text = "Choose a currency", style = MaterialTheme.typography.h5)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)
                }
            }
        }
    }
}

@Composable
fun MainScreen(currencyNames: List<String>?, currencyRates: List<Double>?) {
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

@Composable
fun BottomBar(viewModel: ExchangeViewModel) {
    Surface(elevation = 8.dp, modifier = Modifier.height(75.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        viewModel.changeScreen(Screen.POPULAR)
                    }
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Популярное", color = Color.Blue)
            }

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        viewModel.changeScreen(Screen.FAVORITE)
                    }
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Избранное", color = Color.Blue)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExchangeRatesTestAppPublicTheme {
    }
}