package com.example.exchangeratestestapppublic.domain.model

sealed class Symbol(val value: String) {

    object USD : Symbol("USD")
    object EUR : Symbol("EUR")
    object GBP : Symbol("GBP")
    object CNY : Symbol("CNY")
    object CHF : Symbol("CHF")
    object JPY : Symbol("JPY")
    object UAH : Symbol("UAH")
    object RUB : Symbol("RUB")
    object SEK : Symbol("SEK")
    object TRY : Symbol("TRY")
    object SGD : Symbol("SGD")
    object CAD : Symbol("CAD")
    object DKK : Symbol("DKK")
    object KRW : Symbol("KRW")
    object BRL : Symbol("BRL")
    object INR : Symbol("INR")
    object PLN : Symbol("PLN")
    object AMD : Symbol("AMD")

    class Unknown(value: String) : Symbol(value)

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