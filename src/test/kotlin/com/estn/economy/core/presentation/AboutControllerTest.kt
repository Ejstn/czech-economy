package com.estn.economy.core.presentation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher.matchAll
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest(controllers = [AboutController::class])
class AboutControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `GET oaplikaci zdrojedat returns correct page`() {
        // given
        // when
        // then
        mvc.perform(get(Routing.ABOUT))
                .andExpect(
                        matchAll(
                                status().isOk,
                                view().name("about")
                        )
                )
    }


}