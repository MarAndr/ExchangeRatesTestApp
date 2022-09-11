package com.example.exchangeratestestapppublic.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.domain.ExchangeRepository
import com.example.exchangeratestestapppublic.domain.model.NameModel
import com.example.exchangeratestestapppublic.domain.model.RatesModel
import com.example.exchangeratestestapppublic.domain.model.Symbol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.util.concurrent.TimeUnit
import javax.inject.Inject

data class MainScreenState(
    val activeScreen: Screen = Screen.Popular,
    val chosenCurrency: Symbol? = null,
    val chosenCurrencyName: String? = null,
    val currencyRates: List<RatesModel> = emptyList(),
    val currenciesList: List<NameModel> = emptyList(),
    val ordering: Ordering = Ordering.QUOTE_ASC,
    val favoritesRates: List<RatesModel> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = false
)

@HiltViewModel class ExchangeViewModel @Inject constructor(
    private val repository: ExchangeRepository
) : ViewModel() {

    companion object {

        val MIN_DELAY_REQUEST = TimeUnit.HOURS.toMillis(2)
    }

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreen: StateFlow<MainScreenState> = _mainScreenState

    private var ratesJob: Job? = null

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _mainScreenState.value = _mainScreenState.value.copy(error = throwable)
        Log.e("ExchangeViewModel", "Error: $throwable", throwable)
        _mainScreenState.value = _mainScreenState.value.copy(isLoading = false)
    }
    private val scope = viewModelScope + coroutineExceptionHandler

    init {
        scope.launch {
            getCurrencyNames()
            getRates()
            repository.fetchCurrencyNamesList()
        }
    }

    fun changeQuoteFavorite(isFavorite: Boolean, quote: String) {
        scope.launch {
            repository.changeFavoriteField(isFavorite, quote)
        }
    }

    fun changeOrder(ordering: Ordering) {
        _mainScreenState.value = _mainScreenState.value.copy(ordering = ordering)
        when (_mainScreenState.value.activeScreen) {
            Screen.Popular -> getRates()
            Screen.Favorite -> getFavoriteRates()
        }
    }

    fun changeScreen(screen: Screen) {
        _mainScreenState.value = _mainScreenState.value.copy(activeScreen = screen)
        when (screen) {
            Screen.Popular -> getRates()
            Screen.Favorite -> getFavoriteRates()
        }
    }

    fun changeChosenCurrency(currency: NameModel) {
        _mainScreenState.value = _mainScreenState.value.copy(isLoading = true)
        _mainScreenState.value = _mainScreenState.value.copy(
            chosenCurrency = currency.symbol, chosenCurrencyName = currency.name
        )
        getRates()
        _mainScreenState.value = _mainScreenState.value.copy(isLoading = false)
        getFavoriteRates()
    }

    private fun getCurrencyNames() {
        scope.launch {
            repository.getCurrenciesList().onEach { names ->
                _mainScreenState.value = _mainScreenState.value.copy(currenciesList = names)
            }.collect()
        }
    }

    private fun getRates() {
        _mainScreenState.value.chosenCurrency?.let { base ->
            viewModelScope.launch {
                ratesJob?.cancelAndJoin()
                ratesJob = repository.getCurrencyRatesSorted(
                    base = base, ordering = _mainScreenState.value.ordering
                ).distinctUntilChanged().onEach { rates ->
                    _mainScreenState.value = _mainScreenState.value.copy(currencyRates = rates)
                    fetchRatesIfNeeded(base, rates)
                }.launchIn(scope)
            }
        }
    }

    private suspend fun fetchRatesIfNeeded(base: Symbol, rates: List<RatesModel>) {
        val timestamp = rates.minOfOrNull { it.timestamp } ?: 0L
        val current = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        val timeDif = timestamp + MIN_DELAY_REQUEST - current
        if (rates.isEmpty() || timeDif < 0) {
            repository.fetchLatestCurrency(base)
        }
    }

    private fun getFavoriteRates() {
        _mainScreenState.value.chosenCurrency?.let { base ->
            repository.getFavoriteCurrencyRates(
                base = base, ordering = _mainScreenState.value.ordering
            ).distinctUntilChanged().onEach { rates ->
                _mainScreenState.value = _mainScreenState.value.copy(favoritesRates = rates)
            }.launchIn(scope)
        }
    }
}

enum class Ordering { QUOTE_ASC, QUOTE_DESC, RATE_DESC, RATE_ASC,
}