package com.estn.economy.salary.data.database

import com.estn.economy.salary.data.api.SalaryDto
import java.io.Serializable
import java.time.temporal.IsoFields
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity(name = "average_salary")
@IdClass(SalaryKey::class)
data class SalaryEntity(
        @Column(name = "quarter") @Id val quarter: Int = 0,
        @Column(name = "year") @Id val year: Int = 0,
        @Column(name = "salary_crowns") val salaryCrowns: Int = 0
)

data class SalaryKey(val quarter: Int = 0,
                     val year: Int = 0) : Serializable

val SalaryEntity.key
    get() = SalaryKey(quarter, year)

fun SalaryDto.toEntity(): SalaryEntity {
    return SalaryEntity(
            quarter = this.date.get(IsoFields.QUARTER_OF_YEAR),
            year = this.date.year,
            salaryCrowns = this.salaryCrowns)
}