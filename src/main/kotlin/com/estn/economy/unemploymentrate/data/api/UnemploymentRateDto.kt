package com.estn.economy.unemploymentrate.data.api

import com.estn.economy.core.data.api.converter.CsvRootDto
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate

class UnemploymentRateRootDto : CsvRootDto<UnemploymentRateDto>()

class UnemploymentRateDto {

    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d.M.yyyy")
    var date: LocalDate = LocalDate.now()

    var unemploymentRate: Double? = null

    val isValid: Boolean
        get() = unemploymentRate != null

}

fun UnemploymentRateDto.toEntity(): UnemploymentRateEntity {
    return UnemploymentRateEntity(
            month = this.date.month.value,
            year = this.date.year,
            unemploymentRatePercent = this.unemploymentRate!!)
}