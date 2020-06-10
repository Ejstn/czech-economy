package com.estn.economy.core.presentation.formatting

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.text.DecimalFormat
import java.text.NumberFormat

data class CzechCrowns(val value: Number)

@Component
class CzechCrownsConverter : Converter<CzechCrowns, String> {

    val crowns = "Kč"

    val numberFormat: NumberFormat = DecimalFormat(".00")

    override fun convert(input: CzechCrowns): String {
        return when (input.value) {
            is Int -> "${input.value} $crowns"
            is Double -> "${numberFormat.format(input.value)} $crowns"
            else -> throw IllegalStateException("unknown number type!")
        }
    }
}

val Int.czechCrowns: CzechCrowns
    get() = CzechCrowns(this)

val Double.czechCrowns: CzechCrowns
    get() = CzechCrowns(this)