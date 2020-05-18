package com.estn.economy.nationalbudget.presentation

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [NationalBudgetController::class])
internal class NationalBudgetControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `GET hdp returns correct page`() {
        // given
        // when
        // then
        mvc.perform(MockMvcRequestBuilders.get("/statni-rozpocet"))
                .andExpect(
                        ResultMatcher.matchAll(
                                MockMvcResultMatchers.status().isOk,
                                MockMvcResultMatchers.view().name("pages/national_budget")
                        ))

    }

}