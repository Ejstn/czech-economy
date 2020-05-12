package com.estn.economy.budgetbalance.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BudgetBalanceRepository : JpaRepository<BudgetBalanceEntity, Int> {
}