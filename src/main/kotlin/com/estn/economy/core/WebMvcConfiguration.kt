package com.estn.economy.core

import com.estn.economy.core.presentation.formatting.*
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(private val percent: PercentageConverter,
                          private val quarterAndYear: QuarterAndYearConverter,
                          private val monthAndYear: MonthAndYearConverter,
                          private val czechCrowns: CzechCrownsConverter,
                          private val millionsCzechCrowns: MillionsCzechCrownsConverter) : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        super.addFormatters(registry)
        with(registry) {
            addConverter(percent)
            addConverter(quarterAndYear)
            addConverter(monthAndYear)
            addConverter(czechCrowns)
            addConverter(millionsCzechCrowns)
        }
    }

}
