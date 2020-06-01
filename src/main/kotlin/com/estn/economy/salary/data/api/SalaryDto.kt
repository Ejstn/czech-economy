package com.estn.economy.salary.data.api

import com.estn.economy.core.data.api.converter.CsvRootDto
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate

class SalaryDto {

    companion object {
        const val DEFAULT_SALARY = 0
    }

    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d.M.yyyy")
    var date: LocalDate = LocalDate.now()

    var salaryCrowns: Int = DEFAULT_SALARY

    override fun toString(): String {
        return "SalaryDto(date=$date, salaryCrowns=$salaryCrowns)"
    }

    val isValid: Boolean
        get() = salaryCrowns != DEFAULT_SALARY

}

class SallaryRootDto : CsvRootDto<SalaryDto>()