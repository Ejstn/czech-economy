package com.estn.economy.exchangerate.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class WeirdCNBStringToDoubleDeserializer : JsonDeserializer<Double>() {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Double {
        val string = p?.valueAsString
                ?: throw IllegalStateException("Value that need to be deserialized must be a string in the first place!") as Throwable
        val replace = string.replace(",", ".")
        return replace.toDouble()
    }

}