package com.example.exchangeratestestapppublic.ui

sealed interface Screen {
    object Popular : Screen

    object Favorite : Screen
}