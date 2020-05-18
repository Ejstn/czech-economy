package com.estn.economy.budgetbalance.data

import com.estn.economy.DatabaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.test.context.ActiveProfiles
import java.sql.SQLException


@DatabaseTest
class BudgetBalanceRepositoryTest {

    @Autowired
    lateinit var repository: BudgetBalanceRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
    }

    @Test
    fun `test saving an entity`() {
        // given
        repository.deleteAllInBatch()
        // when
        val entityToSave = BudgetBalanceEntity(year = 2030, millionsCrowns = 15)
        repository.save(entityToSave)
        // then
        val foundRate = repository.findById(entityToSave.year)
        assertThat(foundRate.isPresent).isTrue()
        assertThat(foundRate.get()).isEqualTo(entityToSave)
    }


    @Test
    fun `test saving an entity with 0 year wont work`() {
        // given
        repository.deleteAllInBatch()
        // when
        // then
        assertThrows<JpaSystemException> {
            val entityToSave = BudgetBalanceEntity(year = 0, millionsCrowns = 15)
            repository.save(entityToSave)
        }
    }

}