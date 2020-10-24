package com.estn.economy

import com.estn.economy.core.presentation.model.Routing
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.DisabledIf
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
@DevProfile
class EconomyApplicationEndpointsIntegrationTest {

    @Autowired
    lateinit var restTemplateBuilder: RestTemplateBuilder

    lateinit var restTemplate: RestTemplate

    @LocalServerPort
    var serverPort : Int = 0

    val host = "http://localhost"

    lateinit var baseUrl : String

    @BeforeEach
    fun setUp() {
        restTemplate = restTemplateBuilder.build()
        baseUrl = "$host:$serverPort"
    }

    @Test
    fun `endpoints return 200`() {
        // given
        // when
        // then
        Routing.collectAll()
                .forEach {
                    println(it)
                    `test endpoint returns 200`(it)
                }
    }

    private fun `test endpoint returns 200`(endpoint: String) {
        val url = "$baseUrl$endpoint"
        println("testing: $url what the fuck")
        val entity = restTemplate.getForEntity<String>(url)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

}
