package com.estn.economy.core.domain

import com.estn.economy.core.presentation.utility.PairConvertable
import org.springframework.stereotype.Service

@Service
class CalculateIncrementalValueChangesUseCase {

    fun <T> calculatePercentageChanges(input: Collection<InputData<T>>): List<OutputPercentageData<T>> {
        return input.sortedBy { it.order }
                .windowed(size = 2, step = 1)
                .map { Pair(it[0], it[1]) }
                .map {
                    val first = it.first
                    val second = it.second
                    OutputPercentageData(
                            order = second.order,
                            value = ((second.value.toDouble() / first.value) - 1) * 100,
                            dataPoint =it.second.dataPoint)
                }

    }


}

data class InputData<T>(val order: Long,
                        val value: Long,
                        val dataPoint: T)

data class OutputPercentageData<T>(val order: Long,
                                   val value: Double,
                                   val dataPoint: T) : PairConvertable {

    override fun convertToPair() = Pair(order, value)
}