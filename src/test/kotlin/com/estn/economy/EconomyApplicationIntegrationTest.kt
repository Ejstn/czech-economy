package com.estn.economy

import com.estn.economy.core.presentation.model.Routing
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate

@SpringBootTest
@TestProfile
class EconomyApplicationIntegrationTest {

    @Autowired
    lateinit var restTemplateBuilder: RestTemplateBuilder

    lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setUp() {
        restTemplate = restTemplateBuilder.build()
    }

    @Test
    fun fullSpringContextLoads() {
    }

    @Test
    fun `endpoints return 200`() {
        // given
        // when
        // then
        `test endpoint returns 200`(Routing.DASHBOARD)
        `test endpoint returns 200`(Routing.GDP)
        `test endpoint returns 200`(Routing.INFLATION)
        `test endpoint returns 200`(Routing.UNEMPLOYMENT)
        `test endpoint returns 200`(Routing.NATIONAL_BUDGET)
        `test endpoint returns 200`(Routing.SALARY)
        `test endpoint returns 200`(Routing.EXCHANGE_RATE)
        `test endpoint returns 200`(Routing.ABOUT)
    }

    private fun `test endpoint returns 200`(endpoint: String) {
        val baseUrl = "http://localhost:8080/"
        val entity = restTemplate.getForEntity("$baseUrl$endpoint", String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

}
