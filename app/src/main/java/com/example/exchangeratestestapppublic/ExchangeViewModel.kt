package com.example.exchangeratestestapppublic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainScreenState(
    val activeScreen: Screen = Screen.POPULAR,
    val chosenCurrency: String? = null,
    val currencyRates: List<CurrencyRatesModel> = emptyList(),
    val currenciesList: List<CurrenciesModel> = emptyList(),
    val ordering: Ordering = Ordering.QUOTE_ASC,
    val favoritesRates: List<CurrencyRatesModel> = emptyList(),
    val error: ExchangeApiError = ExchangeApiError(
        isError = false,
        exception = java.lang.Exception()
    )
)

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repo: ExchangeRepository
) : ViewModel() {

    private val _mainScreen = MutableStateFlow(MainScreenState())
    val mainScreen: StateFlow<MainScreenState> = _mainScreen

    private var mainScreenStateValue: MainScreenState
        get() = _mainScreen.value
        set(value) {
            _mainScreen.value = value
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRates()
                getCurrencyNames()
                repo.fetchCurrencyNamesList()
                mainScreenStateValue = mainScreenStateValue.copy(
                    error = ExchangeApiError(
                        isError = true,
                        exception = java.lang.Exception("Test Exception")
                    )
                )
            } catch (e: Exception) {
                mainScreenStateValue = mainScreenStateValue.copy(
                    error = ExchangeApiError(
                        isError = true,
                        exception = e
                    )
                )
                Log.e(this@ExchangeViewModel.javaClass.name, "", e)
            }
        }
    }

    fun changeQuoteFavorite(isFavorite: Boolean, quote: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.changeFavoriteField(isFavorite, quote)
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
                repo.fetchLatestCurrency(currency.symbol)
            } catch (e: Exception) {
                Log.e(this@ExchangeViewModel.javaClass.name, "", e)
            }
        }
    }

    private fun getCurrencyNames() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrenciesList().onEach { names ->
                mainScreenStateValue = mainScreenStateValue.copy(currenciesList = names)
            }.collect()
        }
    }

    private fun getRates() {
        mainScreenStateValue.chosenCurrency?.let { base ->
            repo.getCurrencyRatesSorted(
                base = base,
                ordering = mainScreenStateValue.ordering
            ).distinctUntilChanged()
                .onEach { rates ->
                    mainScreenStateValue = mainScreenStateValue.copy(currencyRates = rates)
                }.launchIn(viewModelScope)
        }
    }

    private fun getFavoriteRates() {
        mainScreenStateValue.chosenCurrency?.let { base ->
            repo.getFavoriteCurrencyRates(
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