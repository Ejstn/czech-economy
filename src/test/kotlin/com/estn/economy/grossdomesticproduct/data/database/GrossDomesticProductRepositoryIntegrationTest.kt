package com.estn.economy.grossdomesticproduct.data.database

import com.estn.economy.grossdomesticproduct.domain.GrossDomesticProductPerYear
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * Written by estn on 17.01.2020.
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GrossDomesticProductRepositoryIntegrationTest {

    @Autowired
    lateinit var repository: GrossDomesticProductRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
    }

    @Test
    fun `test getting all yearly gdps`() {
        // given

        val firstQuater2016 = GrossDomesticProductEntity(quarter = 1, year = 2016, gdpMillionsCrowns = 5)
        val secondQuater2016 = GrossDomesticProductEntity(quarter = 2, year = 2016, gdpMillionsCrowns = 10)

        val firstQuater2019 = GrossDomesticProductEntity(quarter = 1, year = 2019, gdpMillionsCrowns = 25)

        val thirdQuarter2020 = GrossDomesticProductEntity(quarter = 3, year = 2020, gdpMillionsCrowns = 50)
        val fourthQuarter2020 = GrossDomesticProductEntity(quarter = 4, year = 2020, gdpMillionsCrowns = 25)

        repository.saveAll(listOf(
                firstQuater2016,
                secondQuater2016, firstQuater2019,
                thirdQuarter2020, fourthQuarter2020))
        // when
        val result = repository.getAllGdpsSummedByYear()
        // then

        print("\n\n\n RESULT:")
        print(result)
        print("\n\n\n")

        assertThat(result.size).isEqualTo(3)

        assertThat(result.first()).isEqualTo(GrossDomesticProductPerYear(2016, 15))

        assertThat(result[1]).isEqualTo(GrossDomesticProductPerYear(2019, 25))

        assertThat(result[2]).isEqualTo(GrossDomesticProductPerYear(2020, 75))

    }
}