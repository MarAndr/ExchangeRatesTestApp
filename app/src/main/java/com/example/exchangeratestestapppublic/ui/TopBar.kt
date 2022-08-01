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


@Composable
fun TopBar(
    items: List<String>, onClick: (String) -> Unit,
    onSortAscQuoteClick: () -> Unit,
    onSortDescQuoteClick: () -> Unit,
    onSortAscRateClick: () -> Unit,
    onSortDescRateClick: () -> Unit
) {
    Surface(elevation = 8.dp, modifier = Modifier.height(75.dp)) {
        DropdownMenu(
            items = items,
            onClick = onClick,
            onSortAscQuoteClick,
            onSortDescQuoteClick,
            onSortAscRateClick,
            onSortDescRateClick
        )
    }
}

@Composable
fun DropdownMenu(
    items: List<String>, onClick: (String) -> Unit, onSortAscQuoteClick: () -> Unit,
    onSortDescQuoteClick: () -> Unit,
    onSortAscRateClick: () -> Unit,
    onSortDescRateClick: () -> Unit
) {
    var isSortingDialog by remember {
        mutableStateOf(false)
    }
    var expanded by remember { mutableStateOf(false) }
    val disabledValue = "B"
    var selectedIndex: Int? by remember { mutableStateOf(null) }
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .background(Color.White)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            if (selectedIndex != null) {
                onClick(items[selectedIndex!!])
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
            onSortAscQuoteClick = { onSortAscQuoteClick() },
            onSortDescQuoteClick = { onSortDescQuoteClick() },
            onSortAscRateClick = { onSortAscRateClick() },
            onSortDescRateClick = { onSortDescRateClick() },
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