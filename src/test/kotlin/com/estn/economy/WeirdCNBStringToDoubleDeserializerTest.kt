package com.estn.economy

import com.estn.economy.exchangerate.api.WeirdCNBStringToDoubleDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Suppress("MemberVisibilityCanBePrivate")
class WeirdCNBStringToDoubleDeserializerTest {

    val mapper: ObjectMapper = JsonMapper()

    @Test
    fun testStringToDoubleDeserialization() {
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