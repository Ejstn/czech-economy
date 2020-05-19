package com.estn.economy.nationalbudget.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity(name = "public_debt")
data class PublicDebtEntity(
        @Column(name = "year") @Id val year: Int = 0,
        @Column(name = "value_millions_crowns") val millionsCrowns: Long = 0
)