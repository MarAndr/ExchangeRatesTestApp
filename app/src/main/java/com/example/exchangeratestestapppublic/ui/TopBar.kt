package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.exchangeratestestapppublic.R
import com.example.exchangeratestestapppublic.db.CurrenciesModel


@Composable
fun TopBar(
    items: List<CurrenciesModel>,
    onClick: (CurrenciesModel) -> Unit,
    onSortAscQuoteClick: () -> Unit,
    onSortDescQuoteClick: () -> Unit,
    onSortAscRateClick: () -> Unit,
    onSortDescRateClick: () -> Unit
) {
    Surface(elevation = 8.dp, modifier = Modifier.height(75.dp)) {
        DropdownMenu(
            items = items,
            onClick = onClick,
            onSortAscQuoteClick = onSortAscQuoteClick,
            onSortDescQuoteClick = onSortDescQuoteClick,
            onSortAscRateClick = onSortAscRateClick,
            onSortDescRateClick = onSortDescRateClick
        )
    }
}

@Composable
fun DropdownMenu(
    items: List<CurrenciesModel>,
    onClick: (CurrenciesModel) -> Unit,
    onSortAscQuoteClick: () -> Unit,
    onSortDescQuoteClick: () -> Unit,
    onSortAscRateClick: () -> Unit,
    onSortDescRateClick: () -> Unit
) {
    var isSortingDialog by remember {
        mutableStateOf(false)
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex: Int? by remember { mutableStateOf(null) }
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .background(Color.White)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {

            selectedIndex?.let { index ->
                onClick(items[index])
                Text(
                    text = "${items[index].name}(${items[index].symbol})",
                    style = MaterialTheme.typography.h5
                )
            } ?: run {
                Text(text = "Выберите валюту", style = MaterialTheme.typography.h5)
            }
//            if (selectedIndex != null) {
//                onClick(items[selectedIndex!!])
//                Text(
//                    text = "${items[selectedIndex!!].name}(${items[selectedIndex!!].symbol})",
//                    style = MaterialTheme.typography.h5
//                )
//            } else {
//                Text(text = "Выберите валюту", style = MaterialTheme.typography.h5)
//            }

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
                        Text(text = s.name)
                    }
                }
            }
        }

        Box(Modifier.weight(0.2f)) {
            Row {
                Icon(
                    modifier = Modifier.clickable {
                        isSortingDialog = true
                    },
                    painter = painterResource(id = R.drawable.ic_baseline_sort),
                    contentDescription = ""
                )
            }
        }

    }

    if (isSortingDialog) {
        SortingDialog(
            onDismissRequest = { isSortingDialog = false },
            onSortAscQuoteClick = {
                isSortingDialog = false
                onSortAscQuoteClick()
            },
            onSortDescQuoteClick = {
                isSortingDialog = false
                onSortDescQuoteClick()
            },
            onSortAscRateClick = {
                isSortingDialog = false
                onSortAscRateClick()
            },
            onSortDescRateClick = {
                isSortingDialog = false
                onSortDescRateClick()
            },
        )
    }
}

@Composable
fun SortingDialog(
    onDismissRequest: () -> Unit,
    onSortAscQuoteClick: () -> Unit,
    onSortDescQuoteClick: () -> Unit,
    onSortAscRateClick: () -> Unit,
    onSortDescRateClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.clickable { onSortAscQuoteClick() },
                    text = "Отсортировать названия валют по возрастанию",
                    style = MaterialTheme.typography.h5
                )
                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier.clickable { onSortDescQuoteClick() },
                    text = "Отсортировать названия валют по убыванию",
                    style = MaterialTheme.typography.h5
                )

                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier.clickable { onSortAscRateClick() },
                    text = "Отсортировать значения валют по возрастанию",
                    style = MaterialTheme.typography.h5
                )
                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier.clickable { onSortDescRateClick() },
                    text = "Отсортировать значения валют по убыванию",
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}