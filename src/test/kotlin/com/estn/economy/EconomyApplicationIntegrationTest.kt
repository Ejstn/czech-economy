package com.estn.economy

import com.estn.economy.core.presentation.model.Routing
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

    @Value(value = "\${server.port}")
    var serverPort : Int = 0

    val host = "http://localhost"

    lateinit var baseUrl : String

    @BeforeEach
    fun setUp() {
        restTemplate = restTemplateBuilder.build()
        baseUrl = "$host:$serverPort"
    }

    @Test
    fun fullSpringContextLoads() {
    }

    @Test
    fun `endpoints return 200`() {
        // given
        // when
        // then
        Routing.collectAll()
                .forEach {
                    `test endpoint returns 200`(it)
                }
    }

    private fun `test endpoint returns 200`(endpoint: String) {
        val entity = restTemplate.getForEntity("$baseUrl$endpoint", String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

}
