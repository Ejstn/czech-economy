package com.estn.economy.salary.data.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SalaryRepository : JpaRepository<SalaryEntity, SalaryKey>  {
}