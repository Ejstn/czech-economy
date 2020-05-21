package com.estn.economy.unemploymentrate.domain

import com.estn.economy.core.presentation.PairConvertable

data class UnemploymentRatePerYearAvg(val year: Int = 0,
                                      val unemploymentRatePercent: Double = 0.0) : PairConvertable {
    override fun convertToPair() = Pair(year, unemploymentRatePercent)
}