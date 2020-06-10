package com.estn.economy.nationalbudget.domain

import com.estn.economy.nationalbudget.data.BudgetBalanceEntity
import com.estn.economy.nationalbudget.data.BudgetBalanceRepository
import com.estn.economy.nationalbudget.data.PublicDebtEntity
import com.estn.economy.nationalbudget.data.PublicDebtRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class FetchNationalBudgetUseCase(private val budgetBalanceRepository: BudgetBalanceRepository,
                                 private val publicDebtRepository: PublicDebtRepository) {

    @Cacheable("FetchNationalBudgetUseCase::fetchPublicDebt")
    fun fetchPublicDebt(): List<PublicDebtEntity> {
        return publicDebtRepository.findAll()
    }

    @Cacheable("FetchNationalBudgetUseCase::findCurrentPublicDebt")
    fun findCurrentPublicDebt() : PublicDebtEntity = publicDebtRepository.findFirstByOrderByYearDesc()

    @Cacheable("FetchNationalBudgetUseCase::fetchBudgetBalance")
    fun fetchBudgetBalance(): List<BudgetBalanceEntity> {
        return budgetBalanceRepository.findAll()
    }

    @Cacheable("FetchNationalBudgetUseCase::fetchCurrentBudgetBalance")
    fun fetchCurrentBudgetBalance() = budgetBalanceRepository.findFirstByOrderByYearDesc()

}