package com.estn.economy.unemploymentrate.domain

import com.estn.economy.core.data.api.CNBClient
import com.estn.economy.unemploymentrate.data.api.toEntity
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SynchronizeUnemploymentRateUseCase(private val api: CNBClient,
                                         private val repository: UnemploymentRateRepository) {


    private val startingDate = LocalDate.of(1998, 1, 1)

    fun execute() {
        api.fetchMonthlyUnemploymentRates(from = startingDate, to = LocalDate.now())
                .filter {
                    it.isValid
                }
                .map {
                    it.toEntity()
                }
                .toList()
                .also {
                    repository.saveAll(it)
                }
    }

}