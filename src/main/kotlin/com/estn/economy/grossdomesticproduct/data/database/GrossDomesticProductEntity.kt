package com.estn.economy.grossdomesticproduct.data.database

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "gross_domestic_product")
data class GrossDomesticProductEntity(
        @Column(name = "year") @Id var year: Int = 0,
        @Column(name = "value_millions_crowns") var gdpMillionsCrowns: Long = 0
)