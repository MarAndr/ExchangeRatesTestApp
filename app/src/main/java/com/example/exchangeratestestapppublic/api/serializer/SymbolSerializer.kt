package com.example.exchangeratestestapppublic.api.serializer

import com.example.exchangeratestestapppublic.api.model.Symbol
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class SymbolDeserializer : JsonDeserializer<Symbol> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Symbol {
        val str = json.asString

        return try {
            Symbol.valueOf(str)
        } catch (ex: Exception) {
            Symbol.Unknown
        }
    }
}