package com.estn.economy.inflation.data

import java.io.Serializable
import javax.persistence.*

@Entity(name = "inflation_rate")
@IdClass(InflationRateKey::class)
data class InflationRateEntity(
        @Column(name = "month") @Id var month: Int = 0,
        @Column(name = "year") @Id var year: Int = 0,
        @Column(name = "type") @Enumerated(EnumType.STRING) @Id var type: InflationType = InflationType.THIS_YEAR_VS_LAST_YEAR,
        @Column(name = "value_percent") var valuePercent: Float = 0f
)

enum class InflationType {
    THIS_YEAR_VS_LAST_YEAR,
    THIS_MONTH_VS_PREVIOUS_YEARS_MONTH,
    THIS_MONTH_VS_PREVIOUS_MONTH
}

data class InflationRateKey(
        var month: Int = 0,
        var year: Int = 0,
        var type: InflationType = InflationType.THIS_YEAR_VS_LAST_YEAR
) : Serializable

fun InflationRateEntity.key() = InflationRateKey(this.month, this.year, this.type)