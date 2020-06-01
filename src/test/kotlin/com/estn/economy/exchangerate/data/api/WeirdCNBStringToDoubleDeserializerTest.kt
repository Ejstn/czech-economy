package com.estn.economy.exchangerate.data.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest


/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
@JsonTest
class WeirdCNBStringToDoubleDeserializerTest {

    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun `mapper deserializes string double to real double`() {
        // given
        val input = """{"number":"10,5"}"""
        // when
        val result = mapper.readValue<TestObject>(input)
        // then
        assertThat(result.number).isEqualTo(10.5)
    }

    class TestObject {
        @JsonDeserialize(using = WeirdCNBStringToDoubleDeserializer::class)
        val number: Double = 0.0
    }

}