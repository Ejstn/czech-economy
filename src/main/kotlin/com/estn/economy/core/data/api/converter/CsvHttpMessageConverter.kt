package com.estn.economy.core.data.api.converter

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.AbstractHttpMessageConverter
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets
import java.util.*

open class CsvHttpMessageConverter<T, L : CsvRootDto<T>>(
        private val columnSeparator: Char = '|',
        private val supportedMediaTypes: List<MediaType> = listOf(MediaType.TEXT_PLAIN),
        private val hasHeader: Boolean = true)
    : AbstractHttpMessageConverter<L>() {

    private val mapper = CsvMapper().also {
        it.enable(CsvParser.Feature.SKIP_EMPTY_LINES)
    }

    override fun getSupportedMediaTypes() = supportedMediaTypes

    override fun supports(clazz: Class<*>) = CsvRootDto::class.java.isAssignableFrom(clazz)

    override fun readInternal(clazz: Class<out L>, inputMessage: HttpInputMessage): L {
        val itemClass = toBeanType(clazz.genericSuperclass)


        val schema = mapper.schemaFor(itemClass)
                .withColumnSeparator(columnSeparator)
                .let {
                    if (hasHeader)
                        it.withHeader()
                    else
                        it.withoutHeader()
                }

        val result: List<T> = mapper.readerFor(itemClass).with(schema)
                .readValues<T>(getInputReader(inputMessage)).readAll()
                .toList()

        val list = clazz.getDeclaredConstructor().newInstance()
        list?.list = result
        return list
    }

    private fun getInputReader(inputMessage: HttpInputMessage): Reader? {
        /*
         * Respect the charset if specified in the request headers; default to UTF-8 if absent.
         * (Note that we ignore the HTTP 1.1 spec here, which specifies that ISO-8859-1 is the
         * default charset.)
         */
        val contentCharSet = Optional.ofNullable(inputMessage.headers.contentType)
                .map { it.charset }
                .orElse(StandardCharsets.UTF_8)
        return InputStreamReader(inputMessage.body, contentCharSet)
    }

    override fun writeInternal(l: L, outputMessage: HttpOutputMessage) {
        TODO("not implemented")
    }

    private fun toBeanType(type: Type): Class<T> {
        return (type as ParameterizedType).actualTypeArguments[0] as Class<T>
    }


}