package com.example.exchangeratestestapppublic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.ExchangeRepository
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
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
    val chosenCurrency: String? = null,
    val chosenCurrencyName: String? = null,
    val currencyRates: List<CurrencyRatesModel> = emptyList(),
    val currenciesList: List<CurrenciesModel> = emptyList(),
    val ordering: Ordering = Ordering.QUOTE_ASC,
    val favoritesRates: List<CurrencyRatesModel> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: ExchangeRepository
) : ViewModel() {

    companion object {

        val MIN_DELAY_REQUEST = TimeUnit.HOURS.toMillis(2)
    }

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreen: StateFlow<MainScreenState> = _mainScreenState
    private var ratesJob: Job? = null
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mainScreenStateValue = mainScreenStateValue.copy(
            error = throwable
        )
        mainScreenStateValue = mainScreenStateValue.copy(isLoading = false)
    }
    private val scope = viewModelScope + coroutineExceptionHandler + Dispatchers.IO

    private var mainScreenStateValue: MainScreenState
        get() = _mainScreenState.value
        set(value) {
            _mainScreenState.value = value
        }

    init {
        scope.launch() {
            getCurrencyNames()
            getRates()
            repository.fetchCurrencyNamesList()
        }
    }

    fun changeQuoteFavorite(isFavorite: Boolean, quote: String) {
        scope.launch() {
            repository.changeFavoriteField(isFavorite, quote)
        }
    }

    fun changeOrder(ordering: Ordering) {
        scope.launch() {
            mainScreenStateValue = mainScreenStateValue.copy(ordering = ordering)
            when (mainScreenStateValue.activeScreen) {
                Screen.Popular -> getRates()
                Screen.Favorite -> getFavoriteRates()
            }
        }
    }

    fun changeScreen(screen: Screen) {
        scope.launch() {
            mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
            when (screen) {
                Screen.Popular -> getRates()
                Screen.Favorite -> getFavoriteRates()
            }
        }
    }

    fun changeChosenCurrency(currency: CurrenciesModel) {
        scope.launch() {
            mainScreenStateValue = mainScreenStateValue.copy(isLoading = true)
            mainScreenStateValue = mainScreenStateValue.copy(
                chosenCurrency = currency.symbol,
                chosenCurrencyName = currency.name
            )
            getRates()
            mainScreenStateValue = mainScreenStateValue.copy(isLoading = false)
            getFavoriteRates()
        }
    }

    private fun getCurrencyNames() {
        scope.launch() {
            repository.getCurrenciesList().onEach { names ->
                mainScreenStateValue = mainScreenStateValue.copy(currenciesList = names)
            }.collect()
        }
    }

    private fun getRates() {
        mainScreenStateValue.chosenCurrency?.let { base ->
            viewModelScope.launch {
                ratesJob?.cancelAndJoin()
                ratesJob = repository.getCurrencyRatesSorted(
                    base = base,
                    ordering = mainScreenStateValue.ordering
                ).distinctUntilChanged()
                    .onEach { rates ->
                        mainScreenStateValue = mainScreenStateValue.copy(currencyRates = rates)
                        fetchRatesIfNeeded(base, rates)
                    }.launchIn(scope)
            }
        }
    }

    private suspend fun fetchRatesIfNeeded(base: String, rates: List<CurrencyRatesModel>) {
        val timestamp = rates.minOfOrNull { it.timestamp } ?: 0L
        val current = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        val timeDif = timestamp + MIN_DELAY_REQUEST - current
        if (rates.isEmpty() || timeDif < 0) {
            repository.fetchLatestCurrency(base)
        }
    }

    private fun getFavoriteRates() {
        mainScreenStateValue.chosenCurrency?.let { base ->
            repository.getFavoriteCurrencyRates(
                base = base,
                ordering = mainScreenStateValue.ordering
            ).distinctUntilChanged()
                .onEach { rates ->
                    mainScreenStateValue = mainScreenStateValue.copy(favoritesRates = rates)
                }.launchIn(scope)
        }
    }
}

enum class Ordering {
    QUOTE_ASC,
    QUOTE_DESC,
    RATE_DESC,
    RATE_ASC,
}