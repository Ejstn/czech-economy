package com.estn.economy.unemploymentrate.data.database

import com.estn.economy.unemploymentrate.domain.UnemploymentRatePerYearAvg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * Written by estn on 17.01.2020.
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UnemploymentRateRepositoryIntegrationTest {

    @Autowired
    lateinit var repository: UnemploymentRateRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAllInBatch()
    }

    @Test
    fun `test getting all yearly gdps`() {
        // given

        val firstQuater2016 = UnemploymentRateEntity(quarter = 1, year = 2016, unemploymentRatePercent = 5.0)
        val secondQuater2016 = UnemploymentRateEntity(quarter = 2, year = 2016, unemploymentRatePercent = 10.0)
        val thirdQuarter2016 = UnemploymentRateEntity(quarter = 3, year = 2016, unemploymentRatePercent = 15.0)

        val firstQuater2019 = UnemploymentRateEntity(quarter = 1, year = 2019, unemploymentRatePercent = 25.0)

        val thirdQuarter2020 = UnemploymentRateEntity(quarter = 3, year = 2020, unemploymentRatePercent = 50.0)
        val fourthQuarter2020 = UnemploymentRateEntity(quarter = 4, year = 2020, unemploymentRatePercent = 25.0)

        repository.saveAll(listOf(
                firstQuater2016, secondQuater2016, thirdQuarter2016,
                firstQuater2019,
                thirdQuarter2020, fourthQuarter2020))
        // when
        val result = repository.getAllYearlyAveragedUnemploymentRates()
        // then

        print("\n\n\n RESULT:")
        print(result)
        print("\n\n\n")

        assertThat(result.size).isEqualTo(3)

        assertThat(result.first()).isEqualTo(UnemploymentRatePerYearAvg(2016, 10.0))

        assertThat(result[1]).isEqualTo(UnemploymentRatePerYearAvg(2019, 25.0))

        assertThat(result[2]).isEqualTo(UnemploymentRatePerYearAvg(2020, 37.5))

    }
}