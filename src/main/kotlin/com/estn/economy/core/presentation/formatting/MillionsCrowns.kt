package com.estn.economy.core.presentation.formatting

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.text.DecimalFormat
import java.text.NumberFormat

data class MillionsCzechCrowns(val value: Number)

@Component
class MillionsCzechCrownsConverter : Converter<MillionsCzechCrowns, String> {

    val crowns = "Kč"
    val billions = "mld"

    val numberFormat: NumberFormat = DecimalFormat(".00")

    override fun convert(input: MillionsCzechCrowns): String {
        return when (input.value) {
            is Long -> "${input.value / 1000} $billions $crowns"
            is Double -> "${numberFormat.format(input.value / 1000)} $billions $crowns"
            else -> throw IllegalStateException("unknown number type!")
        }
    }
}

val Long.millionsCzechCrowns: MillionsCzechCrowns
    get() = MillionsCzechCrowns(this)

val Double.millionsCzechCrowns: MillionsCzechCrowns
    get() = MillionsCzechCrowns(this)