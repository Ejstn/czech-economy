package com.estn.economy.salary.domain

import com.estn.economy.core.data.api.CNBClient
import com.estn.economy.salary.data.database.SalaryRepository
import com.estn.economy.salary.data.database.toEntity
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SynchronizeSalaryUseCase(private val api: CNBClient,
                               private val repository: SalaryRepository) {

    private val startingDate = LocalDate.of(2000, 1, 1)

    @EventListener(ApplicationReadyEvent::class)
    fun execute() {
        // todo do some other way without event listener

        api.fetchNominalAverageSalaries(from = startingDate, to = LocalDate.now())
                .filter { it.isValid }
                .map { it.toEntity() }
                .toList()
                .also {
                    repository.saveAll(it)
                }
    }

}