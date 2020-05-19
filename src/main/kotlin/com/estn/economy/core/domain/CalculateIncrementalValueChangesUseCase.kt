package com.estn.economy.core.domain

import org.springframework.stereotype.Service

@Service
class CalculateIncrementalValueChangesUseCase {

    fun calculatePercentageChanges(input: Collection<InputData>): List<OutputPercentageData> {
        return input.sortedBy { it.order }
                .windowed(size = 2, step = 1)
                .map {
                    Pair(it[0], it[1])
                }
                .map {
                    val first = it.first
                    val second = it.second
                    OutputPercentageData(second.order, ((second.value.toDouble() / first.value) - 1) * 100)
                }

    }


}

data class InputData(val order: Long,
                     val value: Long)

data class OutputPercentageData(val order: Long,
                                val value: Double)