package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.exchangeratestestapppublic.R
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel

@Composable
fun PopularRatesScreen(currencyRates: List<CurrencyRatesModel>?) {
//    Row(Modifier.verticalScroll(rememberScrollState())) {
//        Row() {
//
//        }
//        Column {
//            currencyNames?.forEach {
//                Text(text = it)
//            }
//        }
//        Column {
//            currencyRates?.forEach {
//                Text(text = it.toString())
//            }
//        }
//        Column {
//            currencyNames?.forEach {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_baseline_star_border_unchecked),
//                    contentDescription = ""
//                )
//            }
//        }
//    }
    Column(Modifier.padding(16.dp)) {
        currencyRates?.forEach { currencyRatesModel ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = currencyRatesModel.quote, style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "=", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = currencyRatesModel.rate.toString(), style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_star_border_unchecked),
                    contentDescription = ""
                )

            }
        }
    }

}
