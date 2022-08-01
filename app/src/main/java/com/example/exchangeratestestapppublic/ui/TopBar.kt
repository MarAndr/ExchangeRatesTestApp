package com.example.exchangeratestestapppublic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TopBar(items: List<String>, onClick: (String) -> Unit) {
    Surface(elevation = 8.dp, modifier = Modifier.height(75.dp)) {
        DropdownMenu(items, onClick)
    }
}

@Composable
fun DropdownMenu(items: List<String>, onClick: (String) -> Unit) {
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
            onClick(items[selectedIndex!!])
            Text(
                text = items[selectedIndex!!],
                style = MaterialTheme.typography.h5
            )
        } else {
            Text(text = "Choose a currency", style = MaterialTheme.typography.h5)
        }

        androidx.compose.material.DropdownMenu(
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