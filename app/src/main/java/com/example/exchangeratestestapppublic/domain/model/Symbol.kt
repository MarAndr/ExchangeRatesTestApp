package com.example.exchangeratestestapppublic.domain.model

sealed interface Symbol {

    object USD : Symbol
    object EUR : Symbol
    object GBP : Symbol
    object CNY : Symbol
    object CHF : Symbol
    object JPY : Symbol
    object UAH : Symbol
    object RUB : Symbol
    object SEK : Symbol
    object TRY : Symbol
    object SGD : Symbol
    object CAD : Symbol
    object DKK : Symbol
    object KRW : Symbol
    object BRL : Symbol
    object INR : Symbol
    object PLN : Symbol
    object AMD : Symbol

    data class Unknown(val actualSymbol: String) : Symbol

    companion object {

        fun values() = arrayOf(
            USD,
            EUR,
            GBP,
            CNY,
            CHF,
            JPY,
            UAH,
            RUB,
            SEK,
            TRY,
            SGD,
            CAD,
            DKK,
            KRW,
            BRL,
            INR,
            PLN,
            AMD,
        )

        fun valueOf(value: String) = when (value) {
            "USD" -> USD
            "EUR" -> EUR
            "GBP" -> GBP
            "CNY" -> CNY
            "CHF" -> CHF
            "JPY" -> JPY
            "UAH" -> UAH
            "RUB" -> RUB
            "SEK" -> SEK
            "TRY" -> TRY
            "SGD" -> SGD
            "CAD" -> CAD
            "DKK" -> DKK
            "KRW" -> KRW
            "BRL" -> BRL
            "INR" -> INR
            "PLN" -> PLN
            "AMD" -> AMD
            else -> Unknown(value)
        }
    }

}