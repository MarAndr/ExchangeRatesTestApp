package com.example.exchangeratestestapppublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

data class MainScreenState(
    val activeScreen: Screen = Screen.POPULAR,
    val chosenCurrency: String? = null,
    val currencyRates: List<CurrencyRatesModel> = emptyList(),
    val currenciesList: List<CurrenciesModel> = emptyList(),
    val ordering: Ordering = Ordering.QUOTE_ASC,
    val favoritesRates: List<CurrencyRatesModel> = emptyList(),
    val error: Exception? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: ExchangeRepository
) : ViewModel() {

    companion object {
        val MIN_DELAY_REQUEST = TimeUnit.HOURS.toMillis(2)
    }

    private val _mainScreen = MutableStateFlow(MainScreenState())
    val mainScreen: StateFlow<MainScreenState> = _mainScreen
    private var ratesJob: Job? = null

    private var mainScreenStateValue: MainScreenState
        get() = _mainScreen.value
        set(value) {
            _mainScreen.value = value
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCurrencyNames()
                getRates()
                mainScreenStateValue = mainScreenStateValue.copy(isLoading = true)
                repository.fetchCurrencyNamesList()
                mainScreenStateValue = mainScreenStateValue.copy(isLoading = false)

            } catch (e: Exception) {
                mainScreenStateValue = mainScreenStateValue.copy(
                    error = e
                )
            }
        }
    }

    fun changeQuoteFavorite(isFavorite: Boolean, quote: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeFavoriteField(isFavorite, quote)
        }
    }

    fun changeOrder(ordering: Ordering) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreenStateValue = mainScreenStateValue.copy(ordering = ordering)
            when (mainScreenStateValue.activeScreen) {
                Screen.POPULAR -> getRates()
                Screen.FAVORITE -> getFavoriteRates()
            }
        }
    }

    fun changeScreen(screen: Screen) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
            when (screen) {
                Screen.POPULAR -> getRates()
                Screen.FAVORITE -> getFavoriteRates()
            }
        }
    }

    fun changeChosenCurrency(currency: CurrenciesModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mainScreenStateValue = mainScreenStateValue.copy(chosenCurrency = currency.symbol)
                getRates()
                getFavoriteRates()
            } catch (e: Exception) {
                mainScreenStateValue = mainScreenStateValue.copy(
                    error = e
                )
            }
        }
    }

    private fun getCurrencyNames() {
        viewModelScope.launch(Dispatchers.IO) {
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
                    }.launchIn(viewModelScope + Dispatchers.IO)
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
                }.launchIn(viewModelScope)
        }
    }
}

enum class Ordering {
    QUOTE_ASC,
    QUOTE_DESC,
    RATE_DESC,
    RATE_ASC,
}