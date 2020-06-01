package com.estn.economy.salary.domain

import com.estn.economy.salary.data.database.SalaryRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class FetchSalaryUseCase (private val repository: SalaryRepository) {

    @Cacheable("FetchSalaryUseCase::fetchAll", unless = "#result.isEmpty()")
    fun fetchAll() = repository.findAll().toList()

    @Cacheable("FetchSalaryUseCase::fetchLatest", unless = "#result == null")
    fun fetchLatest() = repository.findFirstByOrderByYearDescQuarterDesc()

}