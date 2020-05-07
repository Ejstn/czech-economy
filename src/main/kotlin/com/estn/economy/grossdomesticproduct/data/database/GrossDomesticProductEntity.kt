package com.estn.economy.grossdomesticproduct.data.database

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity(name = "gross_domestic_product")
@IdClass(GrossDomesticProductKey::class)
data class GrossDomesticProductEntity(
        @Column(name = "quarter") @Id var quarter: Int = 0,
        @Column(name = "year") @Id var year: Int = 0,
        @Column(name = "value_millions_crowns") var gdpMillionsCrowns: Long = 0
)

data class GrossDomesticProductKey(
        var quarter: Int = 0,
        var year: Int = 0
) : Serializable

fun GrossDomesticProductEntity.key()
        = GrossDomesticProductKey(this.quarter, this.year)