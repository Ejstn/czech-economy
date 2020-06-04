package com.estn.economy.grossdomesticproduct.data.api

import com.estn.economy.core.data.api.converter.CsvRootDto
import com.estn.economy.core.presentation.date.DateFormatter
import com.estn.economy.core.presentation.date.getQuarter
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductEntity
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate
import kotlin.math.roundToLong

class GrossDomesticProductRootDto : CsvRootDto<GrossDomesticProductDto>()

class GrossDomesticProductDto {

    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatter.CNB_ARAD_RESPONSE_FORMAT)
    var date: LocalDate = LocalDate.now()

    var gdpBillionsCrowns: Double? = null

    val isValid: Boolean
        get() = gdpBillionsCrowns != null


}

fun GrossDomesticProductDto.toEntity(type: GrossDomesticProductType) : GrossDomesticProductEntity {
    return GrossDomesticProductEntity(
            year = this.date.year,
            quarter = this.date.getQuarter(),
            type = type,
            gdpMillionsCrowns = (gdpBillionsCrowns!! * 1000).roundToLong()
    )
}