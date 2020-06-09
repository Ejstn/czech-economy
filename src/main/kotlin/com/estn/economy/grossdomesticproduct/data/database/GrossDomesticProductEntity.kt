package com.estn.economy.grossdomesticproduct.data.database

import com.estn.economy.core.presentation.utility.PairConvertable
import java.io.Serializable
import javax.persistence.*

@Entity(name = "gross_domestic_product")
@IdClass(GrossDomesticProductKey::class)
data class GrossDomesticProductEntity(
        @Column(name = "year") @Id var year: Int = 0,
        @Column(name = "quarter") @Id var quarter: Int = 0,
        @Column(name = "type") @Id @Enumerated(EnumType.STRING)
        var type: GrossDomesticProductType = GrossDomesticProductType.NOMINAL,
        @Column(name = "value_millions_crowns") var gdpMillionsCrowns: Long = 0
) : PairConvertable {
    override fun convertToPair(): Pair<Any, Any> {
        return Pair(year, gdpMillionsCrowns)
    }
}

enum class GrossDomesticProductType {
    NOMINAL,
    REAL_2010_PRICES
}

data class GrossDomesticProductKey(
        var year: Int = 0,
        var quarter: Int = 0,
        var type: GrossDomesticProductType = GrossDomesticProductType.NOMINAL
) : Serializable

val GrossDomesticProductEntity.key
    get() = GrossDomesticProductKey(this.year, this.quarter, this.type)