package com.estn.economy.core.presentation.formatting

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.text.DecimalFormat
import java.text.NumberFormat

data class Percentage (val value: Double)

@Component
class PercentageConverter : Converter<Percentage, String> {

    val numberFormat: NumberFormat = DecimalFormat(".0")

    override fun convert(input: Percentage): String? {
        return "${numberFormat.format(input.value)}%"
    }
}

val Float.percentage: Percentage
    get() = Percentage(this.toDouble())

val Double.percentage: Percentage
    get() = Percentage(this)