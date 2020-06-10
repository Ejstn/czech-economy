package com.estn.economy.core.presentation.formatting

import com.estn.economy.core.presentation.date.translate
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.Month

data class MonthAndYear(val month: Int,
                        val year: Int)

@Component
class MonthAndYearConverter : Converter<MonthAndYear, String> {
    override fun convert(input: MonthAndYear)
            = "${Month.of(input.month).translate()} ${input.year}"
}