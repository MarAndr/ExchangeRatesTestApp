package com.example.exchangeratestestapppublic.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.domain.ExchangeRepository
import com.example.exchangeratestestapppublic.domain.model.NameModel
import com.example.exchangeratestestapppublic.domain.model.RatesModel
import com.example.exchangeratestestapppublic.domain.model.Symbol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: ExchangeRepository
) : ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState(isLoading = true))
    val mainScreen: StateFlow<MainScreenState> = _mainScreenState

    init {
        fetchNamesList()
    }

    private fun fetchNamesList() = viewModelScope.launch(Dispatchers.IO) {
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

    fun changeChosenCurrency(nameModel: NameModel) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val currency = repository.fetchLatestCurrency(nameModel.symbol)
            _mainScreenState.value = _mainScreenState.value.copy(
                chosenCurrency = nameModel.symbol,
                chosenCurrencyName = nameModel.name,
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

    fun changeQuoteFavorite(isFavorite: Boolean, quote: String) {
        // todo
    }
}

enum class Ordering {
    QUOTE_ASC,
    QUOTE_DESC,
    RATE_DESC, RATE_ASC,
}