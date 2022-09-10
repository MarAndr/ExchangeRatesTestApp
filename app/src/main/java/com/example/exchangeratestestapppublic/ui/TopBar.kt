package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.exchangeratestestapppublic.R
import com.example.exchangeratestestapppublic.domain.model.NameModel

@Composable
fun TopBar(
    modifier: Modifier,
    mainScreenState: MainScreenState,
    items: List<NameModel>,
    onClick: (NameModel) -> Unit,
    onSortAscQuoteClick: () -> Unit,
    onSortDescQuoteClick: () -> Unit,
    onSortAscRateClick: () -> Unit,
    onSortDescRateClick: () -> Unit
) {
    Surface(elevation = 8.dp, modifier = modifier) {
        DropdownMenu(
            items = items,
            mainScreenState = mainScreenState,
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
    items: List<NameModel>,
    mainScreenState: MainScreenState,
    onClick: (NameModel) -> Unit,
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
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val buttonMaxWidth = screenWidth / 1.5f

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {

            val index = selectedIndex

            Button(
                modifier = Modifier.requiredWidthIn(max = buttonMaxWidth),
                onClick = { expanded = true }
            ) {
                if (index != null) {
                    Text(
                        text = "${items[index].name}(${items[index].symbol})",
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else if (mainScreenState.chosenCurrencyName != null) {
                    Text(
                        text = "${mainScreenState.chosenCurrencyName}(${mainScreenState.chosenCurrency})",
                        style = MaterialTheme.typography.h6,
                        maxLines = 1
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.chose_currency_button),
                        style = MaterialTheme.typography.h6,
                        maxLines = 1
                    )
                }
            }

            if (expanded) {
                Dialog(
                    onDismissRequest = { expanded = false },
                    properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colors.background
                            )
                    ) {
                        itemsIndexed(items) { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedIndex = index
                                expanded = false
                                onClick(items[index])
                            }) {
                                Text(text = s.name, style = MaterialTheme.typography.body1)
                            }
                            Divider(modifier = Modifier.padding(vertical = 16.dp))
                        }
                    }
                }
            }
        }

        Icon(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 20.dp)
            ) {
                isSortingDialog = true
            },
            painter = painterResource(id = R.drawable.ic_baseline_sort),
            contentDescription = ""
        )

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
                    text = stringResource(id = R.string.sort_dialog_name_asc),
                    style = MaterialTheme.typography.h5
                )
                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier.clickable { onSortDescQuoteClick() },
                    text = stringResource(id = R.string.sort_dialog_name_desc),
                    style = MaterialTheme.typography.h5
                )

                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier.clickable { onSortAscRateClick() },
                    text = stringResource(id = R.string.sort_dialog_value_asc),
                    style = MaterialTheme.typography.h5
                )
                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier.clickable { onSortDescRateClick() },
                    text = stringResource(id = R.string.sort_dialog_value_desc),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}