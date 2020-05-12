package com.estn.economy.budgetbalance.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity(name = "budget_balance")
data class BudgetBalanceEntity(
        @Column(name = "year") @Id val year: Int = 0,
        @Column(name = "value_millions_crowns") val millionsCrowns: Long = 0
)