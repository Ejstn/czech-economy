package com.estn.economy.salary.data.api

import com.estn.economy.core.data.api.converter.CsvRootDto
import com.estn.economy.salary.data.database.SalaryEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate
import java.time.temporal.IsoFields

class SalaryDto {

    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d.M.yyyy")
    var date: LocalDate = LocalDate.now()

    var salaryCrowns: Int? = null

    override fun toString(): String {
        return "SalaryDto(date=$date, salaryCrowns=$salaryCrowns)"
    }

    val isValid: Boolean
        get() = salaryCrowns != null

}

class SallaryRootDto : CsvRootDto<SalaryDto>()

fun SalaryDto.toEntity(): SalaryEntity {
    return SalaryEntity(
            quarter = this.date.get(IsoFields.QUARTER_OF_YEAR),
            year = this.date.year,
            salaryCrowns = this.salaryCrowns!!)
}