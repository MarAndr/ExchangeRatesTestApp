package com.example.exchangeratestestapppublic.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.domain.ExchangeRepository
import com.example.exchangeratestestapppublic.domain.model.NameModel
import com.example.exchangeratestestapppublic.domain.model.RatesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainScreenState(
    val activeScreen: Screen = Screen.Popular,
    val chosenCurrency: NameModel? = null,
    val currencyRates: List<RatesModel> = emptyList(),
    val currenciesList: List<NameModel> = emptyList(),
    val ordering: Ordering = Ordering.QUOTE_ASC,
    val favoritesRates: List<RatesModel> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: ExchangeRepository
) : ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState(isLoading = true))
    val mainScreen: StateFlow<MainScreenState> = _mainScreenState

    private var tickerJob: Job? = null

    init {
        fetchNamesList()
    }

    private fun fetchNamesList() = viewModelScope.launch {
        try {
            val names = repository.fetchCurrencyNamesList()
            _mainScreenState.value = _mainScreenState.value.copy(currenciesList = names)
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, "fetchNamesList: ", e)
            _mainScreenState.value = _mainScreenState.value.copy(error = e)
        } finally {
            _mainScreenState.value = _mainScreenState.value.copy(isLoading = false)
        }
    }

    private fun startTicker(chosenCurrency: NameModel) {
        tickerJob?.cancel()

        tickerJob = viewModelScope.launch {
            while (true) {
                fetchRates(chosenCurrency)
                delay(1000)
            }
        }
    }

    fun changeChosenCurrency(nameModel: NameModel) = viewModelScope.launch {
        _mainScreenState.value = _mainScreenState.value.copy(
            chosenCurrency = nameModel
        )

        startTicker(nameModel)
    }

    private fun fetchRates(nameModel: NameModel) = viewModelScope.launch {
        try {
            val currency = repository.fetchLatestCurrency(nameModel.symbol)
            _mainScreenState.value = _mainScreenState.value.copy(
                chosenCurrency = nameModel,
                currencyRates = currency
            )
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, "changeChosenCurrency: ", e)
            _mainScreenState.value = _mainScreenState.value.copy(error = e)
        }
    }

    fun changeOrder(rateDesc: Ordering) {
        // todo
    }

    fun changeScreen(favorite: Screen) {
        // todo
    }

    fun changeQuoteFavorite(isFavorite: Boolean, nameModel: NameModel) {
        repository.setFavourite()
    }
}

enum class Ordering {
    QUOTE_ASC,
    QUOTE_DESC,
    RATE_DESC, RATE_ASC,
}